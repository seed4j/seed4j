package tech.jhipster.lite.generator.server.springboot.springcloud.common.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonDomainService;

@IntegrationTest
class SpringCloudCommonBeanConfigurationIT {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  void shouldGetBean() {
    assertThat(applicationContext.getBean("springCloudCommonService")).isNotNull().isInstanceOf(SpringCloudCommonDomainService.class);
  }
}
