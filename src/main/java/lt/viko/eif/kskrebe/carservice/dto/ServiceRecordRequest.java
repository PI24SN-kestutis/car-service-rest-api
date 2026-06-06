package lt.viko.eif.kskrebe.carservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Užklausa naujam serviso įrašui sukurti arba atnaujinti.
 */
public class ServiceRecordRequest {

    @NotBlank(message = "Serviso tipas yra privalomas")
    private String serviceType;

    private String description;

    @NotNull(message = "Kaina yra privaloma")
    @DecimalMin(value = "0.00", message = "Kaina negali būti neigiama")
    private BigDecimal price;

    private LocalDate serviceDate;

    @NotNull(message = "Automobilio ID yra privalomas")
    private Long carId;

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}