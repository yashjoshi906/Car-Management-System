package co.car.dmo;

import org.springframework.data.domain.Sort;

import lombok.Data;

@Data
public class CarPage {
	private int pageNumber =0;
	private int pageSize = 10;
	private Sort.Direction direction = Sort.Direction.ASC;
	private String sortBy = "year";
}
