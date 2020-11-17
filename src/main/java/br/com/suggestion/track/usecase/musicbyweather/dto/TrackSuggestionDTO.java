package br.com.suggestion.track.usecase.musicbyweather.dto;

import java.io.Serializable;

/**
 * @author Charles Rodrigo
 */
public class TrackSuggestionDTO implements Serializable {
  private static final long serialVersionUID = 3023368451130551542L;

  private String name;
  private String url;
  private String image;
  private String previewUrl;

  public TrackSuggestionDTO(String name, String url, String previewUrl, String image) {
    this.name = name;
    this.url = url;
    this.image = image;
    this.previewUrl = previewUrl;
  }

  public String getName() {
    return this.name;
  }

  public String getUrl() {
    return this.url;
  }

  public String getImage() {
    return this.image;
  }

  public String getPreviewUrl() {
    return this.previewUrl;
  }

}
