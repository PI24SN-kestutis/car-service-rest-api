package lt.viko.eif.kskrebe.carservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * "customer" auto valdytojai/savininkai.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class Customer {

    /**
     * Unique customer identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Customer first name.
     */
    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Customer last name.
     */
    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * Customer email address.
     */
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Customer phone number.
     */
    @Column(length = 50)
    private String phone;

    /**
     * Automobilių sąrašas, priklausantis klientui.
     *
     * @JsonManagedReference naudojama tam, kad serializuojant
     * objektą į JSON būtų įtraukti susiję automobiliai ir būtų
     * išvengta begalinės rekursijos tarp Customer ir Car objektų.
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars = new ArrayList<>();
}