package tech.jhipster.lite.generator.client.tools.playwright.infrastructure.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.client.tools.playwright.domain.PlaywrightDomainService;

@IntegrationTest
class PlaywrightBeanConfigurationIT {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  void shouldGetBean() {
    assertThat(applicationContext.getBean("playwrightService")).isNotNull().isInstanceOf(PlaywrightDomainService.class);
  }
}
