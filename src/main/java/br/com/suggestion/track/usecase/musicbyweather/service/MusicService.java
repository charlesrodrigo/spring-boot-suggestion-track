package br.com.suggestion.track.usecase.musicbyweather.service;

import br.com.suggestion.track.usecase.musicbyweather.dto.PlaylistMusicDTO;

/**
 * @author Charles Rodrigo
 */
public interface MusicService {

  PlaylistMusicDTO getPlaylist(String style);

}
