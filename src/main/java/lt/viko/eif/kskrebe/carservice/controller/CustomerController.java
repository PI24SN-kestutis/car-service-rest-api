package lt.viko.eif.kskrebe.carservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.viko.eif.kskrebe.carservice.model.Customer;
import lt.viko.eif.kskrebe.carservice.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerService.create(customer);
    }

    /**
     * gaunamas klientas pagal id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return customerService.findById(id);
    }

    /**
     * klientas atnaujinamas
     * @param id
     * @param customer
     * @return
     */
    @PutMapping("/{id}")
    public Customer updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody Customer customer) {

        return customerService.update(id, customer);
    }

    /**
     * panaikinamas kliento įrašas pagal id
     * @param id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
    }
}