package com.seed4j.generator.server.springboot.dbmigration.neo4j.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.NEO4J;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.NEO4J_MIGRATIONS;

import com.seed4j.generator.server.springboot.dbmigration.neo4j.application.Neo4jMigrationApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Neo4jMigrationModuleConfiguration {

  @Bean
  Seed4JModuleResource neo4jMigrationsModule(Neo4jMigrationApplicationService neo4jMigrations) {
    return Seed4JModuleResource.builder()
      .slug(NEO4J_MIGRATIONS)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database Migration", "Add neo4j migrations")
      .organization(Seed4JModuleOrganization.builder().addDependency(NEO4J).build())
      .tags("server", "spring", "spring-boot", "database", "migration", "neo4j")
      .factory(neo4jMigrations::buildModule);
  }
}
