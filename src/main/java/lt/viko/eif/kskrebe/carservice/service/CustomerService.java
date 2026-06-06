package lt.viko.eif.kskrebe.carservice.service;

import lombok.RequiredArgsConstructor;
import lt.viko.eif.kskrebe.carservice.model.Customer;
import lt.viko.eif.kskrebe.carservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Grąžina visus klientus.
     */
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }


    /**
     * Išsaugo naują klientą duomenų bazėje.
     *
     * @param customer kliento duomenys
     * @return išsaugotas klientas
     */
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Suranda klientą pagal identifikatorių.
     *
     * @param id kliento identifikatorius
     * @return rastas klientas
     */
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Klientas nerastas"));
    }


    /**
     * Atnaujina kliento duomenis.
     *
     * @param id kliento identifikatorius
     * @param updated atnaujinti duomenys
     * @return atnaujintas klientas
     */
    public Customer update(Long id, Customer updated) {

        Customer customer = findById(id);

        customer.setFirstName(updated.getFirstName());
        customer.setLastName(updated.getLastName());
        customer.setEmail(updated.getEmail());
        customer.setPhone(updated.getPhone());

        return customerRepository.save(customer);
    }

    /**
     * Pašalina klientą.
     *
     * @param id kliento identifikatorius
     */
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }



}
