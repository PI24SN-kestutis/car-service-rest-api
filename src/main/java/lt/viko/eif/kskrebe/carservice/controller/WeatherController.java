package lt.viko.eif.kskrebe.carservice.controller;

import lombok.RequiredArgsConstructor;
import lt.viko.eif.kskrebe.carservice.dto.WeatherResponse;
import lt.viko.eif.kskrebe.carservice.service.WeatherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/{placeCode}")
    public String getWeather(@PathVariable String placeCode) {
        return weatherService.getWeatherByPlaceCode(placeCode);
    }

    @GetMapping({"/pretty/{placeCode}", "/current/{placeCode}"})
    public WeatherResponse getWeatherByPlaceCode(
            @PathVariable String placeCode) {

        return weatherService.getCurrentWeather(placeCode);
    }

}

