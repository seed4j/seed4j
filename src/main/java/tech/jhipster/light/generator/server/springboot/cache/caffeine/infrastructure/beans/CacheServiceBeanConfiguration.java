package tech.jhipster.light.generator.server.springboot.cache.caffeine.infrastructure.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.light.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.light.generator.server.springboot.cache.caffeine.domain.CaffeineDomainService;
import tech.jhipster.light.generator.server.springboot.cache.caffeine.domain.CaffeineService;

@Configuration
public class CacheServiceBeanConfiguration {

  private final BuildToolService buildToolService;

  public CacheServiceBeanConfiguration(BuildToolService buildToolService) {
    this.buildToolService = buildToolService;
  }

  @Bean
  public CaffeineService cacheService() {
    return new CaffeineDomainService(buildToolService);
  }
}
