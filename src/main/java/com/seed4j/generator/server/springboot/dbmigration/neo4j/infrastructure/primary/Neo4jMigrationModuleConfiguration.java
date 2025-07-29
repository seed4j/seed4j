package com.seed4j.generator.server.springboot.dbmigration.neo4j.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.NEO4J;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.NEO4J_MIGRATIONS;

import com.seed4j.generator.server.springboot.dbmigration.neo4j.application.Neo4jMigrationApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Neo4jMigrationModuleConfiguration {

  @Bean
  JHipsterModuleResource neo4jMigrationsModule(Neo4jMigrationApplicationService neo4jMigrations) {
    return JHipsterModuleResource.builder()
      .slug(NEO4J_MIGRATIONS)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database Migration", "Add neo4j migrations")
      .organization(JHipsterModuleOrganization.builder().addDependency(NEO4J).build())
      .tags("server", "spring", "spring-boot", "database", "migration", "neo4j")
      .factory(neo4jMigrations::buildModule);
  }
}
