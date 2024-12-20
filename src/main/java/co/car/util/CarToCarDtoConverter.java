package co.car.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import co.car.dto.CarDTO;
import co.car.entity.Car;
import co.car.entity.FuelType;

@Component
public class CarToCarDtoConverter implements Converter<Car, CarDTO> {

	@Override
	public CarDTO convert(Car c) {
		CarDTO dto = new CarDTO();
		dto.setColor(c.getColor());
		dto.setName(c.getName());
		dto.setModel(c.getModel());
		dto.setTrasmission(c.getTrasmission());
		dto.setYear(c.getYear());
		dto.setColor(c.getColor());
		
		if (c.getFuelType() == FuelType.PETROL) {
			dto.setFuelType(FuelType.PETROL.name());
		}
		if (c.getFuelType() == FuelType.DIESEL) {
			dto.setFuelType(FuelType.DIESEL.name());
		}
		if (c.getFuelType() == FuelType.ELECTRIC) {
			dto.setFuelType(FuelType.ELECTRIC.name());
		}
		if (c.getFuelType() == FuelType.CNG) {
			dto.setFuelType(FuelType.CNG.name());
		}
		if (c.getFuelType() == FuelType.HYBRID) {
			dto.setFuelType(FuelType.HYBRID.name());
		}
		
		return dto;
	}

}
