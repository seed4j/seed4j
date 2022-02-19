package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.infrastructure.config;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.server.springboot.springcloud.eureka.domain.EurekaDomainService;

@IntegrationTest
class EurekaBeanConfigurationIT {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  void shouldGetBean() {
    assertThat(applicationContext.getBean("eurekaService")).isNotNull().isInstanceOf(EurekaDomainService.class);
  }
}
