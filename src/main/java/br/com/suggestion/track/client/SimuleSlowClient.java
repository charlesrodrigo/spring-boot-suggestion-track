package br.com.suggestion.track.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Charles Rodrigo
 */
@FeignClient(value = "SlowClient", url = "${app.slowclient.url}")
public interface SimuleSlowClient {

  @GetMapping()
  void getSlow();

}


