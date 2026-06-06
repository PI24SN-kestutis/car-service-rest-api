package lt.viko.eif.kskrebe.carservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.viko.eif.kskrebe.carservice.model.Car;
import lt.viko.eif.kskrebe.carservice.service.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public List<Car> getAllCars() {
        return carService.findAll();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carService.findById(id);
    }

    @PostMapping
    public Car createCar(@Valid @RequestBody Car car) {
        return carService.create(car);
    }
}
