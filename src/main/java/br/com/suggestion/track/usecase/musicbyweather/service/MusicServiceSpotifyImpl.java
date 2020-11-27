package br.com.suggestion.track.usecase.musicbyweather.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.hc.core5.http.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import br.com.suggestion.track.usecase.musicbyweather.dto.PlaylistMusicDTO;
import br.com.suggestion.track.usecase.musicbyweather.dto.TrackSuggestionDTO;
import br.com.suggestion.track.usecase.weather.domain.StyleMusic;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

/**
 * @author Charles Rodrigo
 */
@Service
public class MusicServiceSpotifyImpl implements MusicService {

  Logger logger = LogManager.getLogger(MusicServiceSpotifyImpl.class);

  private static final String NAME_RESILIENCE = "Spotify";

  @Value("${app.spotify.key}")
  private String clientId;
  @Value("${app.spotify.secret}")
  private String clientSecret;
  @Value("${app.spotify.url-track}")
  private String url;

  private SpotifyApi spotifyApi;

  @Override
  @CircuitBreaker(name = NAME_RESILIENCE)
  @Retry(name = NAME_RESILIENCE, fallbackMethod = "fallbackMethod")
  @Cacheable(value = "getSpotifyTracks", key = "#style.toUpperCase().replaceAll(' ', '')")
  public PlaylistMusicDTO getPlaylist(String style) {
    try {

      this.logger.info("Obtendo a playlist do spotify");

      this.authSpotify();

      this.logger.info("Vamos lá realizando a busca das musicas no spotify");

      final SearchTracksRequest searchTracksRequest = this.spotifyApi.searchTracks(style)
          .market(CountryCode.BR).limit(10).offset(0).includeExternal("audio").build();

      final Paging<Track> trackPaging = searchTracksRequest.execute();

      var tracks = Arrays.asList(trackPaging.getItems());

      var trackSuggestion = tracks.stream().map(item -> {
        String urlTrackSpotify =
            (new StringBuilder(this.url).append(item.getAlbum().getId())).toString();

        return new TrackSuggestionDTO(item.getAlbum().getName(), urlTrackSpotify,
            item.getPreviewUrl(),
            item.getAlbum().getImages().length > 0 ? (item.getAlbum().getImages()[0]).getUrl()
                : null);

      }).collect(Collectors.toList());

      return new PlaylistMusicDTO(style, trackSuggestion);

    } catch (Exception e) {
      this.logger.error(e);
      throw new WebServerException("erro no spotify", e);
    }

  }

  public PlaylistMusicDTO fallbackMethod(String style, Throwable throwable) {
    this.logger.error("OPA, TIVEMOS UM FALLBACK", throwable);

    this.logger.info("vamos sugerir algo para p usuário ficar menos triste");

    // TODO: ISSO AQUI DEVERIA VIR DE UM CACHE OU DE UM BANCO DE DADOS e de preferência aleatorio
    List<TrackSuggestionDTO> trackSuggestions = new ArrayList<>();

    trackSuggestions.add(new TrackSuggestionDTO("POPSTAR (feat. Drake)", "5nNtpPsSUgb9Hlb3dF1gXa",
        "https://p.scdn.co/mp3-preview/f06dde25172503546a3b136fba9822a89866a2d3?cid=e359cda92a394e08a39237346311471b",
        "https://i.scdn.co/image/ab67616d0000b273efaecb4b9cbae7c120d14617"));


    trackSuggestions.add(new TrackSuggestionDTO("POP/STARS", "0UnBZ8laFgLUq5Ty5vbikQ",
        "https://p.scdn.co/mp3-preview/c8c6086726d7cac2697ac2f2b67089b83437368c?cid=e359cda92a394e08a39237346311471b",
        "https://i.scdn.co/image/ab67616d0000b273d1241debb8543af8322a7d6a"));


    trackSuggestions.add(new TrackSuggestionDTO("Popular Monster", "4gxFqhVYU4wp1XDH1KiIo4",
        "https://p.scdn.co/mp3-preview/18707ef9fd54e5d0f14bbc69304284b2af44307c?cid=e359cda92a394e08a39237346311471b",
        "https://i.scdn.co/image/ab67616d0000b2735f4c9262d32be3019e1dda3e"));

    return new PlaylistMusicDTO(StyleMusic.POP.getName(), trackSuggestions);

  }

  private void authSpotify() throws ParseException, SpotifyWebApiException, IOException {
    this.logger.info("estamos autenticando o spotify");
    this.spotifyApi = new SpotifyApi.Builder().setClientId(this.clientId)
        .setClientSecret(this.clientSecret).build();

    var clientCredentialsRequest = this.spotifyApi.clientCredentials().build();
    final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

    this.spotifyApi.setAccessToken(clientCredentials.getAccessToken());
  }



}
