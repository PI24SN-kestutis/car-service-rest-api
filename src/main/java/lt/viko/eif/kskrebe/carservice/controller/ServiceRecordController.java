package lt.viko.eif.kskrebe.carservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.viko.eif.kskrebe.carservice.dto.ServiceRecordRequest;
import lt.viko.eif.kskrebe.carservice.model.ServiceRecord;
import lt.viko.eif.kskrebe.carservice.service.ServiceRecordService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

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

    @GetMapping("/hateoas")
    public CollectionModel<EntityModel<ServiceRecord>>
    getAllServiceRecordsHateoas() {

        List<EntityModel<ServiceRecord>> records =
                serviceRecordService.findAll()
                        .stream()
                        .map(record -> {

                            EntityModel<ServiceRecord> model =
                                    EntityModel.of(record,
                                            linkTo(methodOn(
                                                    ServiceRecordController.class)
                                                    .getServiceRecordByIdHateoas(
                                                            record.getId()))
                                                    .withSelfRel());

                            if (record.getCar() != null) {
                                model.add(
                                        linkTo(methodOn(CarController.class)
                                                .getCarByIdHateoas(
                                                        record.getCar().getId()))
                                                .withRel("car")
                                );
                            }

                            return model;
                        })
                        .toList();

        return CollectionModel.of(records,
                linkTo(methodOn(ServiceRecordController.class)
                        .getAllServiceRecordsHateoas())
                        .withSelfRel());
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

    @GetMapping("/hateoas/{id}")
    public EntityModel<ServiceRecord> getServiceRecordByIdHateoas(
            @PathVariable Long id) {

        ServiceRecord serviceRecord = serviceRecordService.findById(id);

        EntityModel<ServiceRecord> model = EntityModel.of(serviceRecord,
                linkTo(methodOn(ServiceRecordController.class)
                        .getServiceRecordByIdHateoas(id))
                        .withSelfRel(),

                linkTo(methodOn(ServiceRecordController.class)
                        .getAllServiceRecordsHateoas())
                        .withRel("service-records")
        );

        if (serviceRecord.getCar() != null) {

            model.add(
                    linkTo(methodOn(CarController.class)
                            .getCarByIdHateoas(serviceRecord.getCar().getId()))
                            .withRel("car")
            );

            if (serviceRecord.getCar().getCustomer() != null) {
                model.add(
                        linkTo(methodOn(CustomerController.class)
                                .getCustomerByIdHateoas(
                                        serviceRecord.getCar().getCustomer().getId()))
                                .withRel("customer")
                );
            }
        }

        return model;
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