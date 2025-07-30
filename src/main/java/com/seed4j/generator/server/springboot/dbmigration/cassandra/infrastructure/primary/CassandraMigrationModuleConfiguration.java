package com.seed4j.generator.server.springboot.dbmigration.cassandra.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.CASSANDRA;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.CASSANDRA_MIGRATION;

import com.seed4j.generator.server.springboot.dbmigration.cassandra.application.CassandraMigrationApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CassandraMigrationModuleConfiguration {

  @Bean
  JHipsterModuleResource cassandraMigrationModule(CassandraMigrationApplicationService cassandraMigration) {
    return JHipsterModuleResource.builder()
      .slug(CASSANDRA_MIGRATION)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Database Migration", "Add Cassandra Migration tools")
      .organization(JHipsterModuleOrganization.builder().addDependency(CASSANDRA).build())
      .tags("server", "spring", "spring-boot", "database", "migration", "cassandra")
      .factory(cassandraMigration::buildModule);
  }
}
