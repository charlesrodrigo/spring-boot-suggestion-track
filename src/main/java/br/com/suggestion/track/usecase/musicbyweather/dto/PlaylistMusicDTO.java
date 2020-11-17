package br.com.suggestion.track.usecase.musicbyweather.dto;

import java.io.Serializable;
import java.util.List;


/**
 * @author Charles Rodrigo
 */
public class PlaylistMusicDTO implements Serializable {
  private static final long serialVersionUID = -1453609735878584325L;

  private String styleMusic;
  private List<TrackSuggestionDTO> tracksSuggestion;

  public PlaylistMusicDTO(String styleMusic, List<TrackSuggestionDTO> tracksSuggestion) {
    this.styleMusic = styleMusic;
    this.tracksSuggestion = tracksSuggestion;
  }

  public String getStyleMusic() {
    return this.styleMusic;
  }

  public List<TrackSuggestionDTO> getTracksSuggestion() {
    return this.tracksSuggestion;
  }

}
