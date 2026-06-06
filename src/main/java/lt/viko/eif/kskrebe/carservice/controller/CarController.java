package lt.viko.eif.kskrebe.carservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.viko.eif.kskrebe.carservice.model.Car;
import lt.viko.eif.kskrebe.carservice.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST valdiklis automobilių valdymui.
 *
 * Teikia endpointus automobilių sukūrimui, peržiūrai,
 * atnaujinimui ir pašalinimui.
 */
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
    @GetMapping
    public List<Car> getAllCars() {
        return carService.findAll();
    }

    /**
     * Grąžina vieną automobilį pagal identifikatorių.
     *
     * @param id automobilio identifikatorius
     * @return rastas automobilis
     */
    @GetMapping("/{id}")
    public Car getCar(@PathVariable Long id) {
        return carService.findById(id);
    }

    /**
     * Sukuria naują automobilį.
     *
     * @param car automobilio duomenys
     * @return sukurtas automobilis
     */
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
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable Long id) {
        carService.delete(id);
    }
}
