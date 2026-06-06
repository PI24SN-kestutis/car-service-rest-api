package lt.viko.eif.kskrebe.carservice.repository;

import lt.viko.eif.kskrebe.carservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
