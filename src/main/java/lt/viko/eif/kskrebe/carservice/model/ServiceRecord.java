package lt.viko.eif.kskrebe.carservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * serviso įrašas per apsilankymą automobilio serviso sistemoje.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "service_records")
public class ServiceRecord {

    /**
     * Unique service record identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Type of service, for example oil change, tires, diagnostics.
     */
    @NotBlank(message = "Service type is required")
    @Column(name = "service_type", nullable = false)
    private String serviceType;

    /**
     * Detailed service description.
     */
    @Column(length = 1000)
    private String description;

    /**
     * Service price in euros.
     */
    @DecimalMin(value = "0.00", message = "Kaina negali būti neigiama")
    @Column(precision = 10, scale = 2)
    @NotNull(message = "Kaina privaloma")
    private BigDecimal price;

    /**
     * Service date.
     */
    @Column(name = "service_date")
    private LocalDate serviceDate;

    /**
     * Automobilis, kuriam priklauso šis serviso įrašas.
     *
     * @JsonBackReference naudojama kaip atgalinė nuoroda į Car objektą.
     * Serializuojant serviso įrašą į JSON šis laukas nėra įtraukiamas,
     * todėl išvengiama begalinės rekursijos.
     */
    @JsonIgnoreProperties("serviceRecords")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;
}
