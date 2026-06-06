package lt.viko.eif.kskrebe.carservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
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
    @DecimalMin(value = "0.00", message = "Price cannot be negative")
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * Service date.
     */
    @Column(name = "service_date")
    private LocalDate serviceDate;

    /**
     * Car related to this service record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;
}
