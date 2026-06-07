package lt.viko.eif.kskrebe.carservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.viko.eif.kskrebe.carservice.model.Car;
import lt.viko.eif.kskrebe.carservice.service.CarService;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST valdiklis automobilių valdymui.
 *
 * Teikia endpointus automobilių sukūrimui, peržiūrai,
 * atnaujinimui ir pašalinimui.
 */
@Tag(
        name = "Car API",
        description = "Automobilių valdymo REST API"
)
@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    /**
     * Grąžina visų automobilių sąrašą.
     *
     * @return automobilių sąrašas
     */
    @Operation(
            summary = "Gauti visus automobilius",
            description = "Grąžina visų sistemoje esančių automobilių sąrašą."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Automobilių sąrašas sėkmingai grąžintas"),
            @ApiResponse(responseCode = "404", description = "Automobilių nerasta")
    })
    @GetMapping
    public List<Car> getAllCars() {
        return carService.findAll();
    }

    /**
     * Grąžina visų automobilių sąrašą su HATEOAS nuorodomis.
     *
     * @return automobilių kolekcijos modelis su nuorodomis
     */
    @Operation(
            summary = "Gauti visus automobilius (HATEOAS)",
            description = "Grąžina visų sistemoje esančių automobilių sąrašą su HATEOAS naršymo nuorodomis."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Automobilių sąrašas sėkmingai grąžintas")
    })
    @GetMapping("/hateoas")
    public CollectionModel<EntityModel<Car>> getAllCarsHateoas() {
        List<EntityModel<Car>> cars = carService.findAll()
                .stream()
                .map(car -> {
                    EntityModel<Car> model = EntityModel.of(car,
                            linkTo(methodOn(CarController.class)
                                    .getCarByIdHateoas(car.getId()))
                                    .withSelfRel()
                    );

                    if (car.getCustomer() != null) {
                        model.add(linkTo(methodOn(CustomerController.class)
                                .getCustomerByIdHateoas(car.getCustomer().getId()))
                                .withRel("customer"));
                    }

                    return model;
                })
                .toList();

        return CollectionModel.of(cars,
                linkTo(methodOn(CarController.class)
                        .getAllCarsHateoas())
                        .withSelfRel()
        );
    }



    /**
     * Grąžina vieną automobilį pagal identifikatorių.
     *
     * @param id automobilio identifikatorius
     * @return rastas automobilis
     */
    @Operation(
            summary = "Gauti automobilį pagal ID",
            description = "Grąžina automobilį pagal pateiktą ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Automobilis sėkmingai grąžintas"),
            @ApiResponse(responseCode = "404", description = "Automobilis nerastas")
    })
    @GetMapping("/{id}")
    public Car getCar(@PathVariable Long id) {
        return carService.findById(id);
    }

    /**
     * Grąžina vieną automobilį pagal identifikatorių su HATEOAS nuorodomis.
     *
     * @param id automobilio identifikatorius
     * @return automobilio esybės modelis su nuorodomis
     */
    @Operation(
            summary = "Gauti automobilį pagal ID (HATEOAS)",
            description = "Grąžina automobilį pagal pateiktą ID su HATEOAS naršymo nuorodomis."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Automobilis sėkmingai grąžintas"),
            @ApiResponse(responseCode = "404", description = "Automobilis nerastas")
    })
    @GetMapping("/hateoas/{id}")
    public EntityModel<Car> getCarByIdHateoas(@PathVariable Long id) {
        Car car = carService.findById(id);

        EntityModel<Car> model = EntityModel.of(car,
                linkTo(methodOn(CarController.class)
                        .getCarByIdHateoas(id))
                        .withSelfRel(),
                linkTo(methodOn(CarController.class)
                        .getAllCarsHateoas())
                        .withRel("cars")
        );

        if (car.getCustomer() != null) {
            model.add(linkTo(methodOn(CustomerController.class)
                    .getCustomerByIdHateoas(car.getCustomer().getId()))
                    .withRel("customer"));
        }

        return model;
    }

    /**
     * Sukuria naują automobilį.
     *
     * @param car automobilio duomenys
     * @return sukurtas automobilis
     */
    @Operation(
            summary = "Sukurti naują automobilį",
            description = "Sukuria naują automobilį duomenų bazėje."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Automobilis sukurtas"),
            @ApiResponse(responseCode = "400", description = "Neteisingi duomenys")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Car createCar(@Valid @RequestBody Car car) {
        return carService.create(car);
    }

    /**
     * Atnaujina automobilio duomenis.
     *
     * @param id automobilio identifikatorius
     * @param car atnaujinti automobilio duomenys
     * @return atnaujintas automobilis
     */
    @Operation(
            summary = "Atnaujinti automobilį",
            description = "Atnaujina egzistuojančio automobilio informaciją."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Automobilis atnaujintas"),
            @ApiResponse(responseCode = "404", description = "Automobilis nerastas")
    })
    @PutMapping("/{id}")
    public Car updateCar(
            @PathVariable Long id,
            @Valid @RequestBody Car car) {

        return carService.update(id, car);
    }

    /**
     * Pašalina automobilį.
     *
     * @param id automobilio identifikatorius
     */
    @Operation(
            summary = "Ištrinti automobilį",
            description = "Pašalina automobilį iš sistemos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Automobilis ištrintas"),
            @ApiResponse(responseCode = "404", description = "Automobilis nerastas")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable Long id) {
        carService.delete(id);
    }
}