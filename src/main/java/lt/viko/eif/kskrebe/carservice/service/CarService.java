package lt.viko.eif.kskrebe.carservice.service;

import lombok.RequiredArgsConstructor;
import lt.viko.eif.kskrebe.carservice.exception.ResourceNotFoundException;
import lt.viko.eif.kskrebe.carservice.model.Car;
import lt.viko.eif.kskrebe.carservice.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    /**
     * Grąžina visus automobilius.
     *
     * @return automobilių sąrašas
     */
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    /**
     * Suranda automobilį pagal identifikatorių.
     *
     * @param id automobilio identifikatorius
     * @return rastas automobilis
     */
    public Car findById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Automobilis nerastas su id: " + id + "."));
    }

    /**
     * Išsaugo naują automobilį.
     *
     * @param car automobilio duomenys
     * @return išsaugotas automobilis
     */
    public Car create(Car car) {
        return carRepository.save(car);
    }

    /**
     * Atnaujina automobilio duomenis.
     *
     * @param id automobilio identifikatorius
     * @param updated atnaujinti automobilio duomenys
     * @return atnaujintas automobilis
     */
    public Car update(Long id, Car updated) {
        Car car = findById(id);

        car.setBrand(updated.getBrand());
        car.setModel(updated.getModel());
        car.setProductionYear(updated.getProductionYear());
        car.setVin(updated.getVin());
        car.setCustomer(updated.getCustomer());

        return carRepository.save(car);
    }

    /**
     * Pašalina automobilį.
     *
     * @param id automobilio identifikatorius
     */
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

}
