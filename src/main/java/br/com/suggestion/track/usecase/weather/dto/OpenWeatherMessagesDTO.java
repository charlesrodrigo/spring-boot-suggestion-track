package br.com.suggestion.track.usecase.weather.dto;

import java.io.Serializable;

/**
 * @author Charles Rodrigo
 */
public class OpenWeatherMessagesDTO implements Serializable {
  private static final long serialVersionUID = -2687421362859637746L;

  private String base;
  private OpenWeatherMainDTO main;
  private Long visibility;
  private Long dt;
  private Long timezone;
  private Long id;
  private String name;
  private Long cod;

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public String getBase() {
    return this.base;
  }

  public OpenWeatherMainDTO getMain() {
    return this.main;
  }

  public Long getVisibility() {
    return this.visibility;
  }

  public Long getDt() {
    return this.dt;
  }

  public Long getTimezone() {
    return this.timezone;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public Long getCod() {
    return this.cod;
  }

}
