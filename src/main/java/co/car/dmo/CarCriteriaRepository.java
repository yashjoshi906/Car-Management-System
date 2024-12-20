package co.car.dmo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import co.car.entity.Car;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CarCriteriaRepository {

	private final EntityManager entityManager;
	private CriteriaBuilder criteriaBuilder;

	public Page<Car> findAllWithFilters(CarPage carPage, CarSearchCriteria carSearchCriteria) {
		
		criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
		Root<Car> carRoot = criteriaQuery.from(Car.class);
		Predicate predicate = getPredicate(carSearchCriteria, carRoot);
		criteriaQuery.where(predicate);
		setOrder(carPage, criteriaQuery, carRoot);
		TypedQuery<Car> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(carPage.getPageNumber() * carPage.getPageSize());
		typedQuery.setMaxResults(carPage.getPageSize());
		Pageable pageable = getPageable(carPage);
		long carCount = getCarCount(predicate);
		return new PageImpl<>(typedQuery.getResultList(), pageable, carCount);
	}

	private Predicate getPredicate(CarSearchCriteria carSearchCriteria, Root<Car> carRoot) {

		List<Predicate> predicates = new ArrayList<>();

		if (Objects.nonNull(carSearchCriteria.getName())) {
			predicates.add(criteriaBuilder.like(carRoot.get("name"), "%" + carSearchCriteria.getName() + "%"));
		}
		if (Objects.nonNull(carSearchCriteria.getTrasmission())) {
			predicates.add(
					criteriaBuilder.like(carRoot.get("transmission"), "%" + carSearchCriteria.getTrasmission() + "%"));
		}
		if (Objects.nonNull(carSearchCriteria.getFuelType())) {
			predicates.add(criteriaBuilder.like(carRoot.get("fueltype"), "%" + carSearchCriteria.getFuelType() + "%"));
		}
		if (Objects.nonNull(carSearchCriteria.getColor())) {
			predicates.add(criteriaBuilder.like(carRoot.get("color"), "%" + carSearchCriteria.getColor() + "%"));
		}
		if (Objects.nonNull(carSearchCriteria.getModel())) {
			predicates.add(criteriaBuilder.like(carRoot.get("model"), "%" + carSearchCriteria.getModel() + "%"));
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}

	private void setOrder(CarPage carPage, CriteriaQuery<Car> criteriaQuery, Root<Car> carRoot) {

		if (carPage.getDirection().equals(Sort.Direction.ASC)) {
			criteriaQuery.orderBy(criteriaBuilder.asc(carRoot.get(carPage.getSortBy())));
		} else {
			criteriaQuery.orderBy(criteriaBuilder.desc(carRoot.get(carPage.getSortBy())));
		}
	}

	private Pageable getPageable(CarPage carPage) {
		Sort sort = Sort.by(carPage.getDirection(), carPage.getSortBy());
		return PageRequest.of(carPage.getPageNumber(), carPage.getPageSize(), sort);
	}

	private long getCarCount(Predicate predicate) {
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<Car> carRoot = countQuery.from(Car.class);
		countQuery.select(criteriaBuilder.count(carRoot)).where(predicate);
		return entityManager.createQuery(countQuery).getSingleResult();
	}

}
