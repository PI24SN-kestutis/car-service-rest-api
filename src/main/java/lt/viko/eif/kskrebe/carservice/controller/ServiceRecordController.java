package lt.viko.eif.kskrebe.carservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Serviso įrašų API", description = "Serviso įrašų REST API")
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
    @Operation(summary = "Gauti visus serviso įrašus", description = "Grąžina visų serviso įrašų sąrašą")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviso įrašų sąrašas sėkmingai grąžintas"),
            @ApiResponse(responseCode = "404", description = "Serviso įrašų nerasta")
    })
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
    @Operation(summary = "Gauti serviso įrašą pagal ID", description = "Grąžina vieną serviso įrašą pagal identifikatorių")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviso įrašas sėkmingai grąžintas"),
            @ApiResponse(responseCode = "404", description = "Serviso įrašas nerastas")
    })
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
     * @param 'serviceRecord' serviso įrašo duomenys
     * @return sukurtas serviso įrašas
     */
    @Operation(summary = "Sukurti naują serviso įrašą", description = "Sukurti naują serviso įrašą su pateiktais duomenimis")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Serviso įrašas sukurtas"),
            @ApiResponse(responseCode = "400", description = "Neteisingi duomenys")
    })
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
    @Operation(summary = "Atnaujinti serviso įrašą", description = "Atnaujinti serviso įrašą pagal identifikatorių su naujais duomenimis")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviso įrašas atnaujintas"),
            @ApiResponse(responseCode = "404", description = "Serviso įrašas nerastas")
    })
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
    @Operation(summary = "Ištrinti serviso įrašą", description = "Pašalina serviso įrašą pagal identifikatorių")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Serviso įrašas ištrintas"),
            @ApiResponse(responseCode = "404", description = "Serviso įrašas nerastas")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteServiceRecord(@PathVariable Long id) {
        serviceRecordService.delete(id);
    }
}