package br.com.suggestion.track.usecase.weather.dto;

import java.io.Serializable;

/**
 * @author Charles Rodrigo
 */
public class OpenWeatherMainDTO implements Serializable {
  private static final long serialVersionUID = 5726407018473671128L;

  private Double temp;
  private Double feelsLike;
  private Double tempMin;
  private Double tempMax;
  private Long pressure;
  private Long humidity;

  public Double getTemp() {
    return this.temp;
  }

  public Double getFeelsLike() {
    return this.feelsLike;
  }

  public Double getTempMin() {
    return this.tempMin;
  }

  public Double getTempMax() {
    return this.tempMax;
  }

  public Long getPressure() {
    return this.pressure;
  }

  public Long getHumidity() {
    return this.humidity;
  }
}
