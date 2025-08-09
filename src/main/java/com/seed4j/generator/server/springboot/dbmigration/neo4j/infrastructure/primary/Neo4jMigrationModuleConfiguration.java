package com.seed4j.generator.server.springboot.dbmigration.neo4j.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.NEO4J;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.NEO4J_MIGRATIONS;

import com.seed4j.generator.server.springboot.dbmigration.neo4j.application.Neo4jMigrationApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Neo4jMigrationModuleConfiguration {

  @Bean
  SeedModuleResource neo4jMigrationsModule(Neo4jMigrationApplicationService neo4jMigrations) {
    return SeedModuleResource.builder()
      .slug(NEO4J_MIGRATIONS)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database Migration", "Add neo4j migrations")
      .organization(SeedModuleOrganization.builder().addDependency(NEO4J).build())
      .tags("server", "spring", "spring-boot", "database", "migration", "neo4j")
      .factory(neo4jMigrations::buildModule);
  }
}
