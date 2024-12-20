package co.car.util;

import org.springframework.data.jpa.domain.Specification;

import co.car.entity.Car;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CarSpecs {

	public static Specification<Car> hasId(long cid) {

		return (root, query, cb) -> cb.equal(root.get("cid"), cid);
	}

	public static Specification<Car> hasName(String name) {

		return (root, query, cb) -> cb.equal(cb.lower(root.get("name")), name.toLowerCase());
	}

	public static Specification<Car> containsName(String name) {

		return (root, query, cb) -> cb.like(cb.lower(root.get("name")), name.toLowerCase().trim() + "%");
	}

	public static Specification<Car> hasModel(String model) {

		return (root, query, cb) -> cb.equal(cb.lower(root.get("model")), model.toLowerCase());
	}

	public static Specification<Car> hasColor(String color) {

		return (root, query, cb) -> cb.equal(cb.lower(root.get("color")), color.toLowerCase());
	}

	public static Specification<Car> containsColor(String color) {

		return (root, query, cb) -> cb.like(cb.lower(root.get("color")), "%" + color.toLowerCase() + "%");
	}

	public static Specification<Car> hasfuelType(String fuelType) {

		return (root, query, cb) -> cb.equal(cb.lower(root.get("fuelType")), fuelType.toLowerCase());
	}

	public static Specification<Car> containsfuelType(String fuelType) {

		return (root, query, cb) -> cb.like(cb.lower(root.get("fuelType")), "%" + fuelType.toLowerCase() + "%");
	}

	public static Specification<Car> hasYear(int year) {

		return (root, query, cb) -> cb.equal(root.get("year"), year);
	}

	public static Specification<Car> containsYear(int year) {

		return (root, query, cb) -> cb.like(root.get("year"), "%" + year + "%");
	}

	public static Specification<Car> isYearGreaterThan(int year) {

		return (root, query, cb) -> cb.greaterThan(root.get("year"), year);
	}

	public static Specification<Car> isYearLessThan(int year) {

		return (root, query, cb) -> cb.lessThan(root.get("year"), year);
	}
}
