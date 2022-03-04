package tech.jhipster.lite.generator.server.springboot.database.cassandra.infrastructure.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.server.springboot.database.cassandra.domain.CassandraDomainService;
import tech.jhipster.lite.generator.server.springboot.database.mongodb.domain.MongodbDomainService;

@IntegrationTest
public class CassandradbBeanConfigurationIT {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  void shouldGetBean() {
    assertThat(applicationContext.getBean("cassandraService")).isNotNull().isInstanceOf(CassandraDomainService.class);
  }
}
