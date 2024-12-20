package co.car.dmo;

import lombok.Data;

@Data
public class CarSearchCriteria {
	private String model;
	private String name;
	private String color;
	private String trasmission;
	private String fuelType;
}
