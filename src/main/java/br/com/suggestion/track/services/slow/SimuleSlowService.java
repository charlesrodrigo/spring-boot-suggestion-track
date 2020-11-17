package br.com.suggestion.track.services.slow;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Charles Rodrigo
 */
@FeignClient(value = "SlowService", url = "${app.slowservice.url}")
public interface SimuleSlowService {

  @GetMapping()
  void getSlow();

}


