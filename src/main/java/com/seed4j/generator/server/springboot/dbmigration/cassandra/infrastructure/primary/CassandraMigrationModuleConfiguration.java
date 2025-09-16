package com.seed4j.generator.server.springboot.dbmigration.cassandra.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.CASSANDRA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.CASSANDRA_MIGRATION;

import com.seed4j.generator.server.springboot.dbmigration.cassandra.application.CassandraMigrationApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CassandraMigrationModuleConfiguration {

  @Bean
  Seed4JModuleResource cassandraMigrationModule(CassandraMigrationApplicationService cassandraMigration) {
    return Seed4JModuleResource.builder()
      .slug(CASSANDRA_MIGRATION)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Database Migration", "Add Cassandra Migration tools")
      .organization(Seed4JModuleOrganization.builder().addDependency(CASSANDRA).build())
      .tags("server", "spring", "spring-boot", "database", "migration", "cassandra")
      .factory(cassandraMigration::buildModule);
  }
}
