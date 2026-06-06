package lt.viko.eif.kskrebe.carservice.repository;

import lt.viko.eif.kskrebe.carservice.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByCustomerId(Long customerId);
}
