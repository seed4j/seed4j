package com.seed4j.generator.server.springboot.database.neo4j.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.NEO4J;

import com.seed4j.generator.server.springboot.database.neo4j.application.Neo4jApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Neo4jModuleConfiguration {

  @Bean
  JHipsterModuleResource neo4jModule(Neo4jApplicationService neo4j) {
    return JHipsterModuleResource.builder()
      .slug(NEO4J)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder()
          .addBasePackage()
          .addIndentation()
          .addProjectBaseName()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot - Database", "Add Neo4j drivers and dependencies, with testcontainers")
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(neo4j::buildModule);
  }
}
