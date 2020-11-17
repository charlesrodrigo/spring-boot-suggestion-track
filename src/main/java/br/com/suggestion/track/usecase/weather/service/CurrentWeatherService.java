package br.com.suggestion.track.usecase.weather.service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import br.com.suggestion.track.usecase.weather.domain.StyleMusic;
import br.com.suggestion.track.usecase.weather.dto.CurrentWeatherDTO;

/**
 * @author Charles Rodrigo
 */
public interface CurrentWeatherService {

  Optional<CurrentWeatherDTO> getCurrentTemperature(String cityOrLatLong);

  Optional<CurrentWeatherDTO> simuleCurrentTemperatureFallback(String cityOrLatLong);

  default String getStyleMusicByTemperature(Double temperature) {
    if (temperature == null) {
      var rnd = new Random();
      var randonEnumIndex = rnd.nextInt(StyleMusic.values().length);
      return (StyleMusic.values()[randonEnumIndex]).getName();
    }

    StyleMusic styleMusic = Arrays.asList(StyleMusic.values()).stream()
        .filter(item -> temperature > item.getTempInit() && temperature <= item.getTempEnd())
        .findFirst().orElseThrow(IllegalArgumentException::new);

    return styleMusic.getName();
  }

}
