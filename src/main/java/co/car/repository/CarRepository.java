package co.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import co.car.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {
	
//	@Query(value = "select * from car where created_by = :uid ", nativeQuery = true)
//	List<Car> getBlogsByCreatedBy(@Param("uid") Long uid);
}
