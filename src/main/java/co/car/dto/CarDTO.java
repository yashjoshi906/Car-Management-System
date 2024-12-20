package co.car.dto;

import co.car.entity.FuelType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

	@NotBlank(message = "Model cannot be blank")
	@Size(min = 2, max = 50, message = "Model must be between 2 and 50 characters")
	private String model;

	@NotBlank(message = "Name is mandatory")
	@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
	@Column(name = "name", nullable = false)
	private String name;

	@NotBlank(message = "Color cannot be blank")
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Color must contain only alphabets")
	private String color;

	@NotBlank(message = "Transmission cannot be blank")
	@Pattern(regexp = "^(Manual|Automatic|CVT)$", 	message = "Invalid transmission type")
	private String trasmission;

	@NotNull(message = "Year cannot be blank and must be between 1900-2099")
	@Min(1900)
	@Max(2099)
	private int year;

	@NotNull(message = "Price cannot be null")
	@Positive(message = "Price must be a positive value")
	@DecimalMin(value = "0.01", message = "Minimum price should be 0.01")
	@DecimalMax(value = "10000000.00", message = "Price exceeds maximum limit")
	private float price;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Fuel type is mandatory")
	@Pattern(regexp = "^(?i)(petrol|diesel|electric|cng|hybrid)$", 	message = "Invalid fuel type")
	private String fuelType;
}
