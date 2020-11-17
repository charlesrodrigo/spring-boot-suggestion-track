package br.com.suggestion.track.usecase.weather.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import br.com.suggestion.track.usecase.weather.dto.OpenWeatherMessagesDTO;

/**
 * @author Charles Rodrigo
 */
@FeignClient(value = "OpenWeatherFeing", url = "${app.openweather.url}")
public interface OpenWeatherService {

  @GetMapping(value = "/weather?q={cityOrLatLong}&appid=${app.openweather.appid}&units=metric")
  OpenWeatherMessagesDTO getTemperature(@PathVariable String cityOrLatLong);

}


