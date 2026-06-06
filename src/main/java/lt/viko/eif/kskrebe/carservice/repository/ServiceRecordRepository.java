package lt.viko.eif.kskrebe.carservice.repository;

import lt.viko.eif.kskrebe.carservice.model.ServiceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {
}
