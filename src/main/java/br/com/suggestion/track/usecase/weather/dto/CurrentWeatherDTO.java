package br.com.suggestion.track.usecase.weather.dto;

import java.io.Serializable;

/**
 * @author Charles Rodrigo
 */
public class CurrentWeatherDTO implements Serializable {
  private static final long serialVersionUID = -6748860084110038795L;

  private String description;
  private Double temperature;

  public CurrentWeatherDTO(String description, Double temperature) {
    this.description = description;
    this.temperature = temperature;
  }

  public String getDescription() {
    return this.description;
  }


  public Double getTemperature() {
    return this.temperature;
  }



}
