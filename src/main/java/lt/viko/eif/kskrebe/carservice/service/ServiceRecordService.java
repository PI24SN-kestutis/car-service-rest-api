package lt.viko.eif.kskrebe.carservice.service;

import lombok.RequiredArgsConstructor;
import lt.viko.eif.kskrebe.carservice.dto.ServiceRecordRequest;
import lt.viko.eif.kskrebe.carservice.exception.ResourceNotFoundException;
import lt.viko.eif.kskrebe.carservice.model.Car;
import lt.viko.eif.kskrebe.carservice.model.ServiceRecord;
import lt.viko.eif.kskrebe.carservice.repository.CarRepository;
import lt.viko.eif.kskrebe.carservice.repository.ServiceRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceRecordService {

    private final ServiceRecordRepository serviceRecordRepository;
    private final CarRepository carRepository;

    /**
     * Grąžina visus serviso įrašus.
     *
     * @return serviso įrašų sąrašas
     */
    public List<ServiceRecord> findAll() {
        return serviceRecordRepository.findAll();
    }

    /**
     * Suranda serviso įrašą pagal identifikatorių.
     *
     * @param id serviso įrašo identifikatorius
     * @return rastas serviso įrašas
     */
    public ServiceRecord findById(Long id) {
        return serviceRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Serviso įrašas nerastas su id: " + id + "."));
    }

    /**
     * Išsaugo naują serviso įrašą.
     *
     * @param request nauji serviso įrašo duomenys
     * @return išsaugotas serviso įrašas
     */
    public ServiceRecord create(ServiceRecordRequest request) {
        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new RuntimeException("Automobilis nerastas"));

        ServiceRecord serviceRecord = new ServiceRecord();
        serviceRecord.setServiceType(request.getServiceType());
        serviceRecord.setDescription(request.getDescription());
        serviceRecord.setPrice(request.getPrice());
        serviceRecord.setServiceDate(request.getServiceDate());
        serviceRecord.setCar(car);

        return serviceRecordRepository.save(serviceRecord);
    }

    /**
     * Atnaujina serviso įrašo duomenis.
     *
     * @param id serviso įrašo identifikatorius
     * @param updated atnaujinti serviso įrašo duomenys
     * @return atnaujintas serviso įrašas
     */
    public ServiceRecord update(Long id, ServiceRecord updated) {
        ServiceRecord serviceRecord = findById(id);

        serviceRecord.setServiceType(updated.getServiceType());
        serviceRecord.setDescription(updated.getDescription());
        serviceRecord.setPrice(updated.getPrice());
        serviceRecord.setServiceDate(updated.getServiceDate());
        serviceRecord.setCar(updated.getCar());

        return serviceRecordRepository.save(serviceRecord);
    }

    /**
     * Pašalina serviso įrašą.
     *
     * @param id serviso įrašo identifikatorius
     */
    public void delete(Long id) {
        serviceRecordRepository.deleteById(id);
    }


}
