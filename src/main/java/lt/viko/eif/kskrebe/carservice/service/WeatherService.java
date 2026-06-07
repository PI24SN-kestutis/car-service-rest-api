package lt.viko.eif.kskrebe.carservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lt.viko.eif.kskrebe.carservice.exception.WeatherApiException;
import lt.viko.eif.kskrebe.carservice.dto.WeatherResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private static final DateTimeFormatter FORECAST_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final RestClient restClient;


    /**
     * Gets raw long-term weather forecast JSON from Meteo.lt by place code.
     *
     * @param placeCode Meteo.lt place code
     * @return raw JSON response from Meteo.lt
     */
    @Cacheable(value = "weatherRaw", key = "#placeCode")
    public String getWeatherByPlaceCode(String placeCode) {
        return restClient.get()
                .uri("/places/{placeCode}/forecasts/long-term", placeCode)
                .retrieve()
                .body(String.class);
    }

    /**
     * Gets the current weather forecast for a given Meteo.lt place code.
     *
     * <p>The result is cached to avoid repeated external API calls for the same place code.</p>
     *
     * @param placeCode Meteo.lt place code, for example "vilnius"
     * @return simplified current weather response
     */
    @Cacheable(value = "currentWeather", key = "#placeCode")
    public WeatherResponse getCurrentWeather(String placeCode) {
        String responseBody;
        try {
            responseBody = restClient.get()
                    .uri("/places/{placeCode}/forecasts/long-term", placeCode)
                    .retrieve()
                    .body(String.class);
        } catch (RestClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new WeatherApiException(HttpStatus.NOT_FOUND,
                        "Unknown weather place code: " + placeCode);
            }

            throw new WeatherApiException(HttpStatus.BAD_GATEWAY,
                    "Weather API request failed");
        } catch (RestClientException ex) {
            throw new WeatherApiException(HttpStatus.BAD_GATEWAY,
                    "Weather API is unavailable");
        }

        JsonNode root;
        try {
            root = OBJECT_MAPPER.readTree(responseBody);
        } catch (Exception ex) {
            throw new WeatherApiException(HttpStatus.BAD_GATEWAY,
                    "Weather API returned invalid JSON");
        }

        if (root == null) {
            throw new WeatherApiException(HttpStatus.BAD_GATEWAY,
                    "Weather API returned empty response");
        }

        JsonNode forecasts = root.path("forecastTimestamps");

        if (!forecasts.isArray() || forecasts.isEmpty()) {
            throw new WeatherApiException(HttpStatus.BAD_GATEWAY,
                    "Weather API returned no forecast data");
        }

        JsonNode currentForecast = findClosestForecast(forecasts, Instant.now());

        WeatherResponse response = new WeatherResponse();

        response.setPlaceName(
                root.path("place")
                        .path("name")
                        .asText("Unknown place")
        );

        response.setForecastTime(
                currentForecast.path("forecastTimeUtc")
                        .asText("Unknown time")
        );

        response.setTemperature(
                currentForecast.path("airTemperature")
                        .asDouble()
        );

        response.setCondition(
                currentForecast.path("conditionCode")
                        .asText("Unknown condition")
        );



        return response;
    }

    JsonNode findClosestForecast(JsonNode forecasts, Instant currentTime) {
        JsonNode latestPastForecast = null;

        for (JsonNode forecast : forecasts) {
            String forecastTimeText = forecast.path("forecastTimeUtc").asText(null);
            if (forecastTimeText == null || forecastTimeText.isBlank()) {
                continue;
            }

            Instant forecastTime;
            try {
                forecastTime = LocalDateTime
                        .parse(forecastTimeText, FORECAST_TIME_FORMATTER)
                        .toInstant(ZoneOffset.UTC);
            } catch (DateTimeParseException ex) {
                continue;
            }

            if (!forecastTime.isBefore(currentTime)) {
                return forecast;
            }

            latestPastForecast = forecast;
        }

        if (latestPastForecast != null) {
            return latestPastForecast;
        }

        throw new WeatherApiException(HttpStatus.BAD_GATEWAY,
                "Weather API returned no valid forecast timestamps");
    }
}
