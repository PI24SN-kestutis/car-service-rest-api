package lt.viko.eif.kskrebe.carservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.viko.eif.kskrebe.carservice.dto.ServiceRecordRequest;
import lt.viko.eif.kskrebe.carservice.model.ServiceRecord;
import lt.viko.eif.kskrebe.carservice.service.ServiceRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST valdiklis serviso įrašų valdymui.
 *
 * Teikia endpointus serviso įrašų sukūrimui, peržiūrai,
 * atnaujinimui ir pašalinimui.
 */
@RestController
@RequestMapping("/api/service-records")
@RequiredArgsConstructor
public class ServiceRecordController {

    private final ServiceRecordService serviceRecordService;

    /**
     * Grąžina visų serviso įrašų sąrašą.
     *
     * @return serviso įrašų sąrašas
     */
    @GetMapping
    public List<ServiceRecord> getAllServiceRecords() {
        return serviceRecordService.findAll();
    }

    /**
     * Grąžina vieną serviso įrašą pagal identifikatorių.
     *
     * @param id serviso įrašo identifikatorius
     * @return rastas serviso įrašas
     */
    @GetMapping("/{id}")
    public ServiceRecord getServiceRecord(@PathVariable Long id) {
        return serviceRecordService.findById(id);
    }

    /**
     * Sukuria naują serviso įrašą.
     *
     * @param serviceRecord serviso įrašo duomenys
     * @return sukurtas serviso įrašas
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceRecord createServiceRecord(@Valid @RequestBody ServiceRecordRequest request) {
        return serviceRecordService.create(request);
    }

    /**
     * Atnaujina serviso įrašo duomenis.
     *
     * @param id serviso įrašo identifikatorius
     * @param serviceRecord atnaujinti serviso įrašo duomenys
     * @return atnaujintas serviso įrašas
     */
    @PutMapping("/{id}")
    public ServiceRecord updateServiceRecord(
            @PathVariable Long id,
            @Valid @RequestBody ServiceRecord serviceRecord) {

        return serviceRecordService.update(id, serviceRecord);
    }

    /**
     * Pašalina serviso įrašą.
     *
     * @param id serviso įrašo identifikatorius
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteServiceRecord(@PathVariable Long id) {
        serviceRecordService.delete(id);
    }
}