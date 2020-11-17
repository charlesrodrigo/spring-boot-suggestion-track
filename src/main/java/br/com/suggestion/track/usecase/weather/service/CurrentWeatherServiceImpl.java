package br.com.suggestion.track.usecase.weather.service;

import java.util.Optional;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import br.com.suggestion.track.services.slow.SimuleSlowService;
import br.com.suggestion.track.usecase.weather.dto.CurrentWeatherDTO;
import feign.FeignException.NotFound;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

/**
 * @author Charles Rodrigo
 */
@Service
public class CurrentWeatherServiceImpl implements CurrentWeatherService {

  @Value("${message.suggestion.temperature.notfound}")
  private String messageTemperatureNotFound;

  private static final String NAME_RESILIENCE = "OpenWeather";
  Logger logger = LogManager.getLogger(CurrentWeatherServiceImpl.class);

  OpenWeatherService openWeatherFeing;
  CacheManager cacheManager;
  SimuleSlowService slowService;

  public CurrentWeatherServiceImpl(OpenWeatherService openWeatherFeing, CacheManager cacheManager,
      SimuleSlowService slowService) {
    this.openWeatherFeing = openWeatherFeing;
    this.cacheManager = cacheManager;
    this.slowService = slowService;
  }


  @Override
  @CircuitBreaker(name = NAME_RESILIENCE)
  @Retry(name = NAME_RESILIENCE, fallbackMethod = "fallbackGetTemperature")
  @Cacheable(value = "currentweather", key = "#cityOrLatLong.toUpperCase().replaceAll(' ', '')")
  public Optional<CurrentWeatherDTO> getCurrentTemperature(String cityOrLatLong) {
    this.logger.info("Estamos chamado o openweather para saber a temperatura atual na cidade");

    if (cityOrLatLong.equalsIgnoreCase("fallback")) {
      throw new IllegalArgumentException();
    }

    var message = this.openWeatherFeing.getTemperature(cityOrLatLong);
    return Optional.ofNullable(new CurrentWeatherDTO(cityOrLatLong, message.getMain().getTemp()));
  }

  @Override
  @CircuitBreaker(name = NAME_RESILIENCE)
  @Retry(name = NAME_RESILIENCE, fallbackMethod = "fallbackGetTemperature")
  public Optional<CurrentWeatherDTO> simuleCurrentTemperatureFallback(String cityOrLatLong) {
    if (this.isCallSlow()) {
      this.logger.info("chamada lenta");
      this.slowService.getSlow();
    } else {
      this.logger.info("chamada com erro");
      throw new IllegalArgumentException();
    }

    return Optional.empty();

  }

  private boolean isCallSlow() {
    var random = new Random();
    var value = random.nextInt(10);
    System.out.println((value % 2 == 0));
    return (value % 2 == 0);
  }


  public Optional<CurrentWeatherDTO> fallbackGetTemperature(String cityOrLatLong,
      Throwable throwable) {

    if (throwable instanceof NotFound) {
      return Optional.empty();
    }

    // TODO: AQUI DEVERIAMOS ENVIAR UM AVISO PARA ALGUÉM, COM ISSO PODEMOS TER ALGUMA PRO
    // ATIVIDADE
    this.logger.error("Não conseguimos obter a temperatura");
    return Optional.ofNullable(new CurrentWeatherDTO(this.messageTemperatureNotFound, null));
  }



}
