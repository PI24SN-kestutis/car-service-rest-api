package lt.viko.eif.kskrebe.carservice.cucumber;

import io.cucumber.java.lt.Duota;
import io.cucumber.java.lt.Ir;
import io.cucumber.java.lt.Kai;
import io.cucumber.java.lt.Tada;
import lt.viko.eif.kskrebe.carservice.dto.ServiceRecordRequest;
import lt.viko.eif.kskrebe.carservice.model.Car;
import lt.viko.eif.kskrebe.carservice.model.Customer;
import lt.viko.eif.kskrebe.carservice.model.ServiceRecord;
import lt.viko.eif.kskrebe.carservice.service.CarService;
import lt.viko.eif.kskrebe.carservice.service.CustomerService;
import lt.viko.eif.kskrebe.carservice.service.ServiceRecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ServiceRecordSteps {


    private final CustomerService customerService;
    private final CarService carService;
    private final ServiceRecordService serviceRecordService;

    public ServiceRecordSteps(CustomerService customerService, CarService carService, ServiceRecordService serviceRecordService) {
        this.customerService = customerService;
        this.carService = carService;
        this.serviceRecordService = serviceRecordService;
    }

    private Customer customer;
    private Car car;
    private ServiceRecordRequest serviceRecordRequest;
    private ServiceRecord savedServiceRecord;

    @Duota("egzistuoja klientas su automobiliu {string} {string}")
    public void egzistuojaKlientasSuAutomobiliu(String brand, String model) {
        customer = new Customer();
        customer.setFirstName("Jonas");
        customer.setLastName("Jonaitis");
        customer.setEmail(UUID.randomUUID() + "@test.com");
        customer.setPhone("+37060000000");

        customer = customerService.create(customer);

        car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setProductionYear(2022);
        car.setVin("VIN" + UUID.randomUUID().toString().replace("-", "").substring(0, 14));
        car.setCustomer(customer);

        car = carService.create(car);
    }

    @Ir("naujas serviso įrašas su aprašymu {string}")
    public void naujasServisoIrasasSuAprasymu(String description) {
        serviceRecordRequest = new ServiceRecordRequest();
        serviceRecordRequest.setDescription(description);
        serviceRecordRequest.setServiceDate(LocalDate.now());
        serviceRecordRequest.setPrice(BigDecimal.valueOf(120.00));
        serviceRecordRequest.setServiceType("Oil change");
        serviceRecordRequest.setCarId(car.getId());
    }

    @Kai("serviso įrašas išsaugomas automobiliui")
    public void servisoIrasasIssaugomasAutomobiliui() {
        savedServiceRecord = serviceRecordService.create(serviceRecordRequest);
    }

    @Tada("serviso įrašo aprašymas turi būti {string}")
    public void servisoIrasoAprasymasTuriButi(String expectedDescription) {
        assertNotNull(savedServiceRecord);
        assertNotNull(savedServiceRecord.getId());
        assertEquals(expectedDescription, savedServiceRecord.getDescription());
    }
}