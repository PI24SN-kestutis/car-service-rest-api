package lt.viko.eif.kskrebe.carservice.service;

import lombok.RequiredArgsConstructor;
import lt.viko.eif.kskrebe.carservice.model.ServiceRecord;
import lt.viko.eif.kskrebe.carservice.repository.ServiceRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceRecordService {

    private final ServiceRecordRepository serviceRecordRepository;

    /**
     *
     *
     * @ gražina visus paslaugų įrašus
     */
    public List<ServiceRecord> getAllServiceRecords() {
        return serviceRecordRepository.findAll();
    }

}
