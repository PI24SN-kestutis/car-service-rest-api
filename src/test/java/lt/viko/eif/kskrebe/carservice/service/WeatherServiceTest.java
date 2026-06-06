package lt.viko.eif.kskrebe.carservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WeatherService weatherService = new WeatherService(null);

    @Test
    void findClosestForecastReturnsFirstFutureForecast() throws Exception {
        JsonNode forecasts = objectMapper.readTree("""
                [
                  {"forecastTimeUtc":"2026-06-06 14:00:00","airTemperature":17.1,"conditionCode":"cloudy"},
                  {"forecastTimeUtc":"2026-06-06 15:00:00","airTemperature":18.2,"conditionCode":"clear"},
                  {"forecastTimeUtc":"2026-06-06 16:00:00","airTemperature":19.3,"conditionCode":"sunny"}
                ]
                """);

        JsonNode selectedForecast = weatherService.findClosestForecast(
                forecasts,
                Instant.parse("2026-06-06T14:30:00Z")
        );

        assertEquals("2026-06-06 15:00:00", selectedForecast.path("forecastTimeUtc").asText());
    }

    @Test
    void findClosestForecastFallsBackToLastPastForecast() throws Exception {
        JsonNode forecasts = objectMapper.readTree("""
                [
                  {"forecastTimeUtc":"2026-06-06 14:00:00","airTemperature":17.1,"conditionCode":"cloudy"},
                  {"forecastTimeUtc":"2026-06-06 15:00:00","airTemperature":18.2,"conditionCode":"clear"}
                ]
                """);

        JsonNode selectedForecast = weatherService.findClosestForecast(
                forecasts,
                Instant.parse("2026-06-06T18:30:00Z")
        );

        assertEquals("2026-06-06 15:00:00", selectedForecast.path("forecastTimeUtc").asText());
    }
}
