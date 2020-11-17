package br.com.suggestion.track.usecase.weather.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Charles Rodrigo
 */
@SpringBootTest
class CurrentWeatherServiceTest {

  @Autowired
  private CurrentWeatherService currentWeatherServiceImpl;

  @Test
  void testGetStyleClassicalMusicalByTemparature() {

    String result = this.currentWeatherServiceImpl.getStyleMusicByTemperature(-1000d);
    assertEquals("classical", result, "negative temperature result failed");

    result = this.currentWeatherServiceImpl.getStyleMusicByTemperature(10d);
    assertEquals("classical", result, "10 temperature result failed");

  }

  @Test
  void testGetStyleRockMusicalByTemparature() {

    String result = this.currentWeatherServiceImpl.getStyleMusicByTemperature(10.1d);
    assertEquals("rock", result, "10.1 temperature result failed");

    result = this.currentWeatherServiceImpl.getStyleMusicByTemperature(15d);
    assertEquals("rock", result, "15 temperature result failed");

  }

  @Test
  void testGetStylePopMusicalByTemparature() {
    String result = this.currentWeatherServiceImpl.getStyleMusicByTemperature(15.1d);
    assertEquals("pop", result, "15.1 temperature result failed");

    result = this.currentWeatherServiceImpl.getStyleMusicByTemperature(30d);
    assertEquals("pop", result, "30 temperature result failed");

  }

  @Test
  void testGetStylePartyMusicalByTemparature() {
    String result = this.currentWeatherServiceImpl.getStyleMusicByTemperature(30.1d);
    assertEquals("party", result, "30.1 temperature result failed");

    result = this.currentWeatherServiceImpl.getStyleMusicByTemperature(300d);
    assertEquals("party", result, "300 temperature result failed");

  }


}
