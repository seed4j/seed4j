package tech.jhipster.lite.generator.client.vue.security.jwt.infrastructure.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.client.vue.security.jwt.domain.VueJwtDomainService;

@IntegrationTest
class VueJwtBeanConfigurationIT {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  void shouldGetBean() {
    assertThat(applicationContext.getBean("vueJwtService")).isNotNull().isInstanceOf(VueJwtDomainService.class);
  }
}
