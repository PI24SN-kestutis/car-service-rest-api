package lt.viko.eif.kskrebe.carservice.service;

import lombok.RequiredArgsConstructor;
import lt.viko.eif.kskrebe.carservice.model.Car;
import lt.viko.eif.kskrebe.carservice.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    /**
     *
     * @gražina visus automobilius.
     */
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new RuntimeException("Automobilis nerastas"));
    }

    public Car create(Car car) {
        return carRepository.save(car);
    }

    public Car update(Long id, Car updated) {
        return carRepository.save(updated);
    }

    public void delete(Long id) {
        carRepository.deleteById(id);
    }
}
