package com.seed4j.generator.server.springboot.dbmigration.cassandra.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.CASSANDRA;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.CASSANDRA_MIGRATION;

import com.seed4j.generator.server.springboot.dbmigration.cassandra.application.CassandraMigrationApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CassandraMigrationModuleConfiguration {

  @Bean
  SeedModuleResource cassandraMigrationModule(CassandraMigrationApplicationService cassandraMigration) {
    return SeedModuleResource.builder()
      .slug(CASSANDRA_MIGRATION)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Database Migration", "Add Cassandra Migration tools")
      .organization(SeedModuleOrganization.builder().addDependency(CASSANDRA).build())
      .tags("server", "spring", "spring-boot", "database", "migration", "cassandra")
      .factory(cassandraMigration::buildModule);
  }
}
