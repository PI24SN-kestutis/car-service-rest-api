package lt.viko.eif.kskrebe.carservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.viko.eif.kskrebe.carservice.model.Customer;
import lt.viko.eif.kskrebe.carservice.service.CustomerService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;

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

    @GetMapping("/hateoas")
    public CollectionModel<EntityModel<Customer>> getAllCustomersHateoas() {
        List<EntityModel<Customer>> customers = customerService.findAll()
                .stream()
                .map(customer -> EntityModel.of(customer,
                        linkTo(methodOn(CustomerController.class)
                                .getCustomerByIdHateoas(customer.getId()))
                                .withSelfRel()
                ))
                .toList();

        return CollectionModel.of(customers,
                linkTo(methodOn(CustomerController.class)
                        .getAllCustomersHateoas())
                        .withSelfRel()
        );
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
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.findById(id);
    }


    @GetMapping("/hateoas/{id}")
    public EntityModel<Customer> getCustomerByIdHateoas(@PathVariable Long id) {
        Customer customer = customerService.findById(id);

        return EntityModel.of(customer,
                linkTo(methodOn(CustomerController.class)
                        .getCustomerByIdHateoas(id))
                        .withSelfRel(),
                linkTo(methodOn(CustomerController.class)
                        .getAllCustomersHateoas())
                        .withRel("customers")
        );
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