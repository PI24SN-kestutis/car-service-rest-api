package lt.viko.eif.kskrebe.carservice.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.lt.Duota;
import io.cucumber.java.lt.Kai;
import io.cucumber.java.lt.Tada;

import lt.viko.eif.kskrebe.carservice.model.Customer;
import lt.viko.eif.kskrebe.carservice.repository.CustomerRepository;
import lt.viko.eif.kskrebe.carservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CustomerSteps {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    public CustomerSteps(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    private Customer customer;

    private Customer savedCustomer;


    @Before
    public void cleanDatabase() {
        customerRepository.deleteAll();
        Customer customer = new Customer();
        customer.setFirstName("Jonas");
        customer.setLastName("Jonaitis");
        customer.setEmail("jonas@test.com");
        customerRepository.save(customer);
    }

    @Duota("naujas klientas vardu {string} ir pavarde {string}")
    public void naujasKlientasVarduIrPavarde(
            String firstName,
            String lastName) {

        customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(firstName.toLowerCase()
                + System.currentTimeMillis()
                + "@test.com");
        customer.setPhone("+37060000000");
    }

    @Kai("klientas išsaugomas")
    public void klientasIssaugomas() {
        savedCustomer = customerService.create(customer);
    }

    @Tada("kliento vardas turi būti {string}")
    public void klientoVardasTuriButi(String expectedName) {
        assertEquals(expectedName, savedCustomer.getFirstName());
    }
}
