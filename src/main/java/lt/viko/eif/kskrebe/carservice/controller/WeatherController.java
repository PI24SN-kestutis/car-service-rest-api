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
 * REST valdiklis, skirtas orų prognozių galiniams taškams (endpoints).
 *
 * <p>Šis valdiklis atveria galinius taškus, kurie naudoja Meteo.lt orų duomenis.
 * Jis pateikia tiek neapdorotus prognozių duomenis, tiek supaprastintus dabartinių orų duomenis.</p>
 */
@Tag(name="Orų prognozių API", description = "Orų prognozių REST API")
@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    /**
     * Grąžina neapdorotus ilgalaikės orų prognozės duomenis pagal Meteo.lt vietos kodą.
     *
     * @param placeCode Meteo.lt vietos kodas, pavyzdžiui "vilnius"
     * @return neapdoroti orų prognozės JSON duomenys kaip tekstas (String)
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
     * Grąžina supaprastintą dabartinę orų prognozę pagal Meteo.lt vietos kodą.
     *
     * @param placeCode Meteo.lt vietos kodas, pavyzdžiui "vilnius"
     * @return supaprastintas dabartinių orų atsakymas
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
