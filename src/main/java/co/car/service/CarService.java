package co.car.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import co.car.entity.Car;

public interface CarService {

	public Optional<Boolean> createVehicleModel(List<Car> vm);
	
	public Car findById(int i);
	
	public Car addCar(Car c);

	public Car updateCar(Car c, long id);

	public boolean deleteCar(long c);

	public Page<Car> cars(String name, String model, int year, Pageable pageable);

	public Page<Car> searchCar(String name, String model, int year, String color, String fuelType,
			Pageable pageable);
}
