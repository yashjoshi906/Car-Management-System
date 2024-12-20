package co.car.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import co.car.dto.CarDTO;
import co.car.entity.Car;
import co.car.entity.FuelType;
import co.car.error.BusinessException;

@Component
public class CarDtoToCarConverter implements Converter<CarDTO, Car> {

	@Override
	public Car convert(CarDTO dto) {
		Car c = new Car();
		c.setColor(dto.getColor());
		c.setColor(dto.getColor());
		c.setName(dto.getName());
		c.setModel(dto.getModel());
		c.setTrasmission(dto.getTrasmission());
		c.setYear(dto.getYear());
		c.setColor(dto.getColor());
		if (dto.getFuelType().equalsIgnoreCase("petrol")) {
			c.setFuelType(FuelType.PETROL);
		}
		if (dto.getFuelType().equalsIgnoreCase("diesel") ) {
			c.setFuelType(FuelType.DIESEL);
		}
		if (dto.getFuelType().equalsIgnoreCase("electric") ) {
			c.setFuelType(FuelType.ELECTRIC);
		}
		if (dto.getFuelType().equalsIgnoreCase("cng") ) {
			c.setFuelType(FuelType.CNG);
		}
		if (dto.getFuelType().equalsIgnoreCase("hybrid") ) {
			c.setFuelType(FuelType.HYBRID);
		}
		c.setPrice(dto.getPrice());
		return c;
	}

}
