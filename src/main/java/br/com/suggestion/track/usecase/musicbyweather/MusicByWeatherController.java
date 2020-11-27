package br.com.suggestion.track.usecase.musicbyweather;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import br.com.suggestion.track.usecase.musicbyweather.dto.PlaylistMusicDTO;
import br.com.suggestion.track.usecase.musicbyweather.service.MusicService;
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
public class MusicByWeatherController {

  MusicService musicService;

  CurrentWeatherService currentWeatherService;

  public MusicByWeatherController(MusicService music, CurrentWeatherService currentWeatherService) {
    this.musicService = music;
    this.currentWeatherService = currentWeatherService;
  }

  @Operation(summary = "get the track according to the city or latitude and longitude")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the tracks",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = PlaylistMusicDTO.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
      @ApiResponse(responseCode = "404", description = "tracks not found", content = @Content)})
  @GetMapping("/musicbycurrentweather")
  public PlaylistMusicDTO getMusic(@RequestParam("q") String cityOrLatLong) {
    CurrentWeatherDTO currentWeatherEntity =
        this.currentWeatherService.getCurrentTemperature(cityOrLatLong).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource"));

    var musicStyle = this.currentWeatherService
        .getStyleMusicByTemperature(currentWeatherEntity.getTemperature());

    return this.musicService.getPlaylist(musicStyle);

  }

}
