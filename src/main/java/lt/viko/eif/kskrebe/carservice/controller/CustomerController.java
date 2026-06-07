package lt.viko.eif.kskrebe.carservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

/**
 * REST valdiklis klientų valdymui.
 *
 * Teikia endpointus klientų sukūrimui, peržiūrai,
 * atnaujinimui ir pašalinimui.
 */
@Tag(
        name = "Customer API",
        description = "Klientų valdymo REST API"
)
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Grąžina visų klientų sąrašą.
     *
     * @return klientų sąrašas
     */
    @Operation(
            summary = "Gauti visus klientus",
            description = "Grąžina visų sistemoje esančių klientų sąrašą."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Klientų sąrašas sėkmingai grąžintas")
    })
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    /**
     * Grąžina visų klientų sąrašą su HATEOAS nuorodomis.
     *
     * @return klientų kolekcijos modelis su nuorodomis
     */
    @Operation(
            summary = "Gauti visus klientus (HATEOAS)",
            description = "Grąžina visų sistemoje esančių klientų sąrašą su HATEOAS naršymo nuorodomis."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Klientų sąrašas sėkmingai grąžintas")
    })
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


    /**
     * Sukuria naują klientą.
     *
     * @param customer kliento duomenys
     * @return sukurtas klientas
     */
    @Operation(
            summary = "Sukurti naują klientą",
            description = "Sukuria naują klientą duomenų bazėje."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Klientas sukurtas"),
            @ApiResponse(responseCode = "400", description = "Neteisingi duomenys")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerService.create(customer);
    }

    /**
     * Grąžina vieną klientą pagal identifikatorių.
     *
     * @param id kliento identifikatorius
     * @return rastas klientas
     */
    @Operation(
            summary = "Gauti klientą pagal ID",
            description = "Grąžina kliento informaciją pagal pateiktą ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Klientas sėkmingai grąžintas"),
            @ApiResponse(responseCode = "404", description = "Klientas nerastas")

    })
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    /**
     * Grąžina vieną klientą pagal identifikatorių su HATEOAS nuorodomis.
     *
     * @param id kliento identifikatorius
     * @return kliento esybės modelis su nuorodomis
     */
    @Operation(
            summary = "Gauti klientą pagal ID (HATEOAS)",
            description = "Grąžina kliento informaciją pagal pateiktą ID su HATEOAS naršymo nuorodomis."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Klientas sėkmingai grąžintas"),
            @ApiResponse(responseCode = "404", description = "Klientas nerastas")
    })
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
     * Atnaujina kliento duomenis.
     *
     * @param id kliento identifikatorius
     * @param customer atnaujinti kliento duomenys
     * @return atnaujintas klientas
     */
    @Operation(
            summary = "Atnaujinti klientą",
            description = "Atnaujina egzistuojančio kliento informaciją."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Klientas atnaujintas"),
            @ApiResponse(responseCode = "404", description = "Klientas nerastas")
    })
    @PutMapping("/{id}")
    public Customer updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody Customer customer) {

        return customerService.update(id, customer);
    }

    /**
     * Pašalina klientą.
     *
     * @param id kliento identifikatorius
     */
    @Operation(
            summary = "Ištrinti klientą",
            description = "Pašalina klientą iš sistemos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Klientas ištrintas"),
            @ApiResponse(responseCode = "404", description = "Klientas nerastas")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
    }
}