package co.car.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long cid;

	private String model;

	private String name;

	private String color;

	private String trasmission;

	private int year;

	private float price;

	@Enumerated(EnumType.STRING)
	private FuelType fuelType;

}
