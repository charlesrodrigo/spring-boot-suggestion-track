package br.com.suggestion.track.usecase.weather.domain;

/**
 * @author Charles Rodrigo
 */
public enum StyleMusic {

  CLASSICAL("classical", null, 10d), ROCK("rock", 10d, 15d), POP("pop", 15d, 30d), PARTY("party",
      30d, null);

  private String name;
  private double tempInit;
  private double tempEnd;

  private StyleMusic(String name, Double tempInit, Double tempEnd) {
    this.name = name;
    this.tempInit = tempInit == null ? -Double.MAX_VALUE : tempInit;
    this.tempEnd = tempEnd == null ? Double.MAX_VALUE : tempEnd;
  }

  public String getName() {
    return this.name;
  }

  public double getTempInit() {
    return this.tempInit;
  }

  public double getTempEnd() {
    return this.tempEnd;
  }

}
