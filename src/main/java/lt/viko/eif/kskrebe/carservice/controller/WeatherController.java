package lt.viko.eif.kskrebe.carservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lt.viko.eif.kskrebe.carservice.dto.WeatherResponse;
import lt.viko.eif.kskrebe.carservice.service.WeatherService;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for weather forecast endpoints.
 *
 * <p>This controller exposes endpoints that use Meteo.lt weather data.
 * It provides both raw forecast data and simplified current weather data.</p>
 */
@Tag(name="Orų prognozių API", description = "Orų prognozių REST API")
@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    /**
     * Returns raw long-term weather forecast data by Meteo.lt place code.
     *
     * @param placeCode Meteo.lt place code, for example "vilnius"
     * @return raw weather forecast JSON as String
     */

    @Operation(summary = "Gauti orų prognozę pagal vietos kodą", description = "Grąžina orų prognozę nurodytam vietos kodui")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sėkmingai gauta orų prognozė"),
            @ApiResponse(responseCode = "404", description = "Vietos kodas nerastas")
    })
    @GetMapping("/{placeCode}")
    public String getWeather(@PathVariable String placeCode) {
        return weatherService.getWeatherByPlaceCode(placeCode);
    }

    /**
     * Returns simplified current weather forecast by Meteo.lt place code.
     *
     * @param placeCode Meteo.lt place code, for example "vilnius"
     * @return simplified current weather response
     */

    @Operation(summary = "Gauti dabartinę orų prognozę pagal vietos kodą", description = "Grąžina dabartinę orų prognozę nurodytam vietos kodui")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sėkmingai gauta dabartinė orų prognozė"),
            @ApiResponse(responseCode = "404", description = "Vietos kodas nerastas")
    })
    @GetMapping({"/pretty/{placeCode}", "/current/{placeCode}"})
    public WeatherResponse getWeatherByPlaceCode(
            @PathVariable String placeCode) {

        return weatherService.getCurrentWeather(placeCode);
    }

}

