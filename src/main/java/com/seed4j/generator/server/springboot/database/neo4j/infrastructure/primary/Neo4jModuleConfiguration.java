package com.seed4j.generator.server.springboot.database.neo4j.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.NEO4J;

import com.seed4j.generator.server.springboot.database.neo4j.application.Neo4jApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Neo4jModuleConfiguration {

  @Bean
  Seed4JModuleResource neo4jModule(Neo4jApplicationService neo4j) {
    return Seed4JModuleResource.builder()
      .slug(NEO4J)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addIndentation()
          .addProjectBaseName()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot - Database", "Add Neo4j drivers and dependencies, with testcontainers")
      .organization(Seed4JModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(neo4j::buildModule);
  }
}
