package br.com.suggestion.track.usecase.weather;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import br.com.suggestion.track.usecase.weather.dto.CurrentWeatherDTO;
import br.com.suggestion.track.usecase.weather.service.CurrentWeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * @author Charles Rodrigo
 */
@RestController
public class WeatherController {

  CurrentWeatherService currentWeatherService;

  public WeatherController(CurrentWeatherService currentWeatherService) {
    this.currentWeatherService = currentWeatherService;
  }


  @Operation(summary = "get the temperature according to the city or latitude and longitude")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the temperature",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = CurrentWeatherDTO.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
      @ApiResponse(responseCode = "404", description = "Temperature not found",
          content = @Content)})
  @GetMapping("/currentweather")
  public CurrentWeatherDTO getTemperature(@RequestParam("q") String cityOrLatLong) {
    return this.currentWeatherService.getCurrentTemperature(cityOrLatLong).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource"));
  }

  @Operation(summary = "get the temperature according to the city or latitude and longitude")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the temperature",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = CurrentWeatherDTO.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
      @ApiResponse(responseCode = "404", description = "Temperature not found",
          content = @Content)})
  @GetMapping("/currentweatherfallback")
  public CurrentWeatherDTO simuleCurrentTemperatureFallback(
      @RequestParam("q") String cityOrLatLong) {
    return this.currentWeatherService.simuleCurrentTemperatureFallback(cityOrLatLong).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource"));
  }



}
