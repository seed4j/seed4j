package tech.jhipster.lite.generator.server.springboot.database.cassandra.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.database.cassandra.application.CassandraApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class CassandraModuleConfiguration {

  @Bean
  JHipsterModuleResource cassandraModule(CassandraApplicationService cassandra) {
    return JHipsterModuleResource
      .builder()
      .slug(CASSANDRA)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Database", "Add Cassandra drivers and dependencies")
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(cassandra::buildModule);
  }
}
