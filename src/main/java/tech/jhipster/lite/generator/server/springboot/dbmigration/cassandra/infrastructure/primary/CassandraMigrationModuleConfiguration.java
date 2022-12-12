package tech.jhipster.lite.generator.server.springboot.dbmigration.cassandra.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.dbmigration.cassandra.application.CassandraMigrationApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class CassandraMigrationModuleConfiguration {

  @Bean
  JHipsterModuleResource cassandraMigrationModule(CassandraMigrationApplicationService cassandraMigration) {
    return JHipsterModuleResource
      .builder()
      .slug(CASSANDRA_MIGRATION)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Database Migration", "Add Cassandra Migration tools")
      .organization(JHipsterModuleOrganization.builder().addDependency(CASSANDRA).build())
      .tags("server", "spring", "spring-boot", "database", "migration", "cassandra")
      .factory(cassandraMigration::buildModule);
  }
}
