package lt.viko.eif.kskrebe.carservice.service;

import lt.viko.eif.kskrebe.carservice.exception.ResourceNotFoundException;
import lt.viko.eif.kskrebe.carservice.model.Car;
import lt.viko.eif.kskrebe.carservice.repository.CarRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/***
 * Unit tests for CarService.
 * Mockito is used to mock CarRepository.
 * Tests verify:
 * - retrieving all cars
 * - retrieving car by id
 * - creating a car
 * - throwing an exception when car is not found
 * The database is not used during unit testing.
 */


@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    void shouldReturnAllCars() {

        Car car1 = new Car();
        car1.setId(1L);

        Car car2 = new Car();
        car2.setId(2L);

        when(carRepository.findAll())
                .thenReturn(List.of(car1, car2));

        List<Car> cars = carService.findAll();

        assertEquals(2, cars.size());

        verify(carRepository).findAll();
    }

    @Test
    void shouldUpdateCar() {

        Car existing = new Car();
        existing.setId(1L);
        existing.setBrand("Audi");

        Car updated = new Car();
        updated.setBrand("Volvo");

        when(carRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(carRepository.save(any(Car.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Car result = carService.update(1L, updated);

        assertEquals("Volvo", result.getBrand());
    }

    @Test
    void shouldReturnCarById() {

        Car car = new Car();
        car.setId(1L);
        car.setBrand("Volvo");

        when(carRepository.findById(1L))
                .thenReturn(Optional.of(car));

        Car result = carService.findById(1L);

        assertEquals("Volvo", result.getBrand());

        verify(carRepository).findById(1L);
    }

    @Test
    void shouldCreateCar() {

        Car car = new Car();
        car.setBrand("Volvo");
        car.setModel("XC60");

        when(carRepository.save(any(Car.class)))
                .thenReturn(car);

        Car result = carService.create(car);

        assertEquals("Volvo", result.getBrand());

        verify(carRepository).save(car);
    }

    @Test
    void shouldThrowExceptionWhenCarNotFound() {

        when(carRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> carService.findById(99L));

        verify(carRepository).findById(99L);
    }
}