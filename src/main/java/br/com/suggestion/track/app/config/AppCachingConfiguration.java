package br.com.suggestion.track.app.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Configuration;



/**
 * @author Charles Rodrigo
 */
@Configuration
public class AppCachingConfiguration extends CachingConfigurerSupport {

  @Override
  public CacheErrorHandler errorHandler() {
    return new AppCacheErrorHandler();
  }

}
