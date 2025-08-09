package com.seed4j.generator.server.springboot.database.neo4j.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.NEO4J;

import com.seed4j.generator.server.springboot.database.neo4j.application.Neo4jApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Neo4jModuleConfiguration {

  @Bean
  SeedModuleResource neo4jModule(Neo4jApplicationService neo4j) {
    return SeedModuleResource.builder()
      .slug(NEO4J)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder()
          .addBasePackage()
          .addIndentation()
          .addProjectBaseName()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot - Database", "Add Neo4j drivers and dependencies, with testcontainers")
      .organization(SeedModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(neo4j::buildModule);
  }
}
