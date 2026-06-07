package lt.viko.eif.kskrebe.carservice.service;

import lt.viko.eif.kskrebe.carservice.exception.ResourceNotFoundException;
import lt.viko.eif.kskrebe.carservice.model.Customer;
import lt.viko.eif.kskrebe.carservice.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CustomerService.
 *
 * Mockito is used to mock CustomerRepository.
 * Tests verify:
 * - retrieving all customers
 * - retrieving customer by id
 * - creating a customer
 * - throwing an exception when customer is not found
 * The database is not used during unit testing.
 */

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest{

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldReturnAllCustomers() {

        Customer customer1 = new Customer();
        customer1.setId(1L);

        Customer customer2 = new Customer();
        customer2.setId(2L);

        when(customerRepository.findAll())
                .thenReturn(List.of(customer1, customer2));

        List<Customer> customers = customerService.findAll();

        assertEquals(2, customers.size());

        verify(customerRepository, times(1))
                .findAll();
    }

    @Test
    void shouldReturnCustomerById() {

        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        Customer result = customerService.findById(1L);

        assertEquals(1L, result.getId());

        verify(customerRepository)
                .findById(1L);
    }

    @Test
    void shouldCreateCustomer() {

        Customer customer = new Customer();
        customer.setFirstName("Jonas");

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        Customer result = customerService.create(customer);

        assertEquals("Jonas", result.getFirstName());

        verify(customerRepository)
                .save(customer);
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {

        when(customerRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> customerService.findById(99L));

        verify(customerRepository)
                .findById(99L);
    }
}