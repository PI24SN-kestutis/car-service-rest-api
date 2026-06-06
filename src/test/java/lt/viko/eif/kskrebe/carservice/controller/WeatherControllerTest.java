package lt.viko.eif.kskrebe.carservice.controller;

import lt.viko.eif.kskrebe.carservice.dto.WeatherResponse;
import lt.viko.eif.kskrebe.carservice.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WeatherControllerTest {

    @Test
    void prettyEndpointReturnsStructuredWeatherResponse() throws Exception {
        WeatherService weatherService = mock(WeatherService.class);
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new WeatherController(weatherService))
                .build();

        WeatherResponse response = new WeatherResponse();
        response.setPlaceName("Vilnius");
        response.setForecastTime("2026-06-06 15:00:00");
        response.setTemperature(18.2);
        response.setCondition("clear");

        when(weatherService.getCurrentWeather("vilnius")).thenReturn(response);

        mockMvc.perform(get("/api/weather/pretty/vilnius"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.placeName").value("Vilnius"))
                .andExpect(jsonPath("$.forecastTime").value("2026-06-06 15:00:00"))
                .andExpect(jsonPath("$.temperature").value(18.2))
                .andExpect(jsonPath("$.condition").value("clear"));
    }
}
