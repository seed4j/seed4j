package tech.jhipster.lite.generator.server.springboot.cache.ehcache.infrastructure.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.server.springboot.cache.ehcache.domain.EhcacheDomainService;

@IntegrationTest
class EhcacheBeanConfigurationIT {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  void shouldGetBean() {
    assertThat(applicationContext.getBean("ehcacheService")).isNotNull().isInstanceOf(EhcacheDomainService.class);
  }
}
