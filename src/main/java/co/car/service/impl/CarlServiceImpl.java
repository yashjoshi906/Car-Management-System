package co.car.service.impl;

import static co.car.util.CarSpecs.hasColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import co.car.entity.Car;
import co.car.repository.CarRepository;
import co.car.service.CarService;
import co.car.util.CarSpecs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarlServiceImpl implements CarService {

	private final CarRepository repo;

	public Optional<Boolean> createVehicleModel(List<Car> vm) {
		List<Car> ls = new ArrayList<>();
		ls = repo.saveAll(vm);
		return Optional.of(!ls.isEmpty());
	}

	public Car addCar(Car c) {
		return repo.save(c);
	}

	@Override
	public Page<Car> cars(String name, String model, int year, Pageable pageable) {
		
		
		Specification<Car> spec = Specification.where(null);
		
		boolean hasNoFilters = 
			    (!StringUtils.hasLength(name)) && 
			    (year <= 0) && 
			    (!StringUtils.hasLength(model));
		
		
		if(hasNoFilters) return repo.findAll(pageable);
		
		if (StringUtils.hasLength(name)) {
			spec = spec.or(CarSpecs.hasName(name));
		}
		if (StringUtils.hasLength(String.valueOf(year))) {
			spec = spec.or(CarSpecs.hasYear(year));
		}
		if (StringUtils.hasLength(model)) {
			spec = spec.or(CarSpecs.hasModel(model));
		}
		return repo.findAll(spec, pageable);
	}

	@Override
	public Page<Car> searchCar(String name, String model, int year, String color, String fuelType, Pageable pageable) {
		Specification<Car> spec = Specification.where(null);
		
		boolean hasNoFilters = 
			    (!StringUtils.hasLength(name)) && 
			    (year <= 0) && 
			    (!StringUtils.hasLength(model)) && 
			    (!StringUtils.hasLength(color)) &&
			    (!StringUtils.hasLength(fuelType)) 
			    ;
		if(hasNoFilters) { return repo.findAll(pageable); }
		
		if (StringUtils.hasLength(name)) {
			spec = spec.or(CarSpecs.containsName(name));
		}
		if (StringUtils.hasLength(model)) {
			spec = spec.or(CarSpecs.hasModel(model));
		}
		if (StringUtils.hasLength(color)) {
			spec = spec.or(hasColor(color));
		}
		if (StringUtils.hasLength(fuelType)) {
			spec = spec.or(CarSpecs.hasfuelType(fuelType));
		}
		if (StringUtils.hasLength(String.valueOf(year))) {
			spec = spec.or(CarSpecs.hasYear(year));
		}
		return repo.findAll(spec, pageable);
	}

	@Override
	public Car findById(int i) {
		return repo.findById(i + 0l).get();
	}

	@Override
	public Car updateCar(Car c, long id) {
		Car updateCar = new Car();
		updateCar.setCid(id);
		updateCar.setName(c.getName());
		updateCar.setColor(c.getColor());
		updateCar.setModel(c.getModel());
		updateCar.setTrasmission(c.getTrasmission());
		updateCar.setFuelType(c.getFuelType());
		updateCar.setPrice(c.getPrice());
		updateCar.setYear(c.getYear());
		return repo.save(updateCar);
	}

	@Override
	public boolean deleteCar(long c) {
		Optional<Car> delete_car = repo.findById(c);
		repo.delete(delete_car.get());
		return false;
	}

}
