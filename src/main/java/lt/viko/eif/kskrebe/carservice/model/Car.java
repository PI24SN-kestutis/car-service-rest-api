package lt.viko.eif.kskrebe.carservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * automobilis, registruotas serviso sistemoje.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cars")
public class Car {

    /**
     * Unique car identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Car brand, for example Volvo, BMW, Audi.
     */
    @NotBlank(message = "Brand is required")
    @Column(nullable = false)
    private String brand;

    /**
     * Car model.
     */
    @NotBlank(message = "Model is required")
    @Column(nullable = false)
    private String model;

    /**
     * Car production year.
     */
    @Min(value = 1900, message = "Production year must be greater than 1900")
    @Max(value = 2100, message = "Production year must be less than 2100")
    @Column(name = "production_year")
    private int productionYear;

    /**
     * Vehicle identification number.
     */
    @NotBlank(message = "VIN is required")
    @Column(nullable = false, unique = true, length = 50)
    private String vin;

    /**
     * Customer who owns this car.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /**
     * įrašai/istorija automobilio serviso.
     */
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceRecord> serviceRecords = new ArrayList<>();
}
