package br.com.suggestion.track.app.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

/**
 * @author Charles Rodrigo
 */
public class AppCacheErrorHandler implements CacheErrorHandler {

  Logger logger = LogManager.getLogger(AppCacheErrorHandler.class);

  @Override
  public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
    // TODO: enviar algum aviso ???
    this.logger.error("handleCacheGetError");

  }

  @Override
  public void handleCachePutError(RuntimeException exception, Cache cache, Object key,
      Object value) {
    // TODO: enviar algum aviso ???
    this.logger.error("handleCachePutError");

  }

  @Override
  public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
    // TODO: enviar algum aviso ???
    this.logger.error("handleCacheEvictError");

  }

  @Override
  public void handleCacheClearError(RuntimeException exception, Cache cache) {
    // TODO: enviar algum aviso ???
    this.logger.error("handleCacheClearError");

  }

}
