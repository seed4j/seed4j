package tech.jhipster.lite.generator.server.springboot.database.mssql.infrastructure.config;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.server.springboot.database.mssql.domain.MsSQLDomainService;

@IntegrationTest
class MsSQLBeanConfigurationIT {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  void shouldGetBean() {
    assertThat(applicationContext.getBean("msSQLService")).isNotNull().isInstanceOf(MsSQLDomainService.class);
  }
}
