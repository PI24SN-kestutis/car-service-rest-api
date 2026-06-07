package lt.viko.eif.kskrebe.carservice.cucumber;

import io.cucumber.java.lt.Duota;
import io.cucumber.java.lt.Ir;
import io.cucumber.java.lt.Kai;
import io.cucumber.java.lt.Tada;
import lt.viko.eif.kskrebe.carservice.model.Car;
import lt.viko.eif.kskrebe.carservice.model.Customer;
import lt.viko.eif.kskrebe.carservice.service.CarService;
import lt.viko.eif.kskrebe.carservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CarSteps {


    private final CustomerService customerService;
    private final CarService carService;

    public CarSteps(CustomerService customerService, CarService carService) {
        this.customerService = customerService;
        this.carService = carService;
    }

    private Customer customer;
    private Car car;
    private Car savedCar;

    @Duota("egzistuoja klientas vardu {string} ir pavarde {string}")
    public void egzistuojaKlientasVarduIrPavarde(String firstName, String lastName) {
        customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(UUID.randomUUID() + "@test.com");
        customer.setPhone("+37060000000");

        customer = customerService.create(customer);
    }

    @Ir("naujas automobilis markės {string} modelio {string}")
    public void naujasAutomobilisMarkesModelio(String brand, String model) {
        car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setProductionYear(2022);
        car.setVin("VIN" + UUID.randomUUID().toString().replace("-", "").substring(0, 14));
        car.setCustomer(customer);
    }

    @Kai("automobilis išsaugomas klientui")
    public void automobilisIssaugomasKlientui() {
        savedCar = carService.create(car);
    }

    @Tada("automobilio markė turi būti {string}")
    public void automobilioMarkeTuriButi(String expectedBrand) {
        assertNotNull(savedCar);
        assertNotNull(savedCar.getId());
        assertEquals(expectedBrand, savedCar.getBrand());
    }
}