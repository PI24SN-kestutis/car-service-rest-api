package lt.viko.eif.kskrebe.carservice.dto;

import lombok.Data;

@Data
public class WeatherResponse {

    private String placeName;
    private String forecastTime;
    private Double temperature;
    private String condition;
}