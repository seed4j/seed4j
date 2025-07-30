package com.seed4j.generator.server.springboot.dbmigration.mongock.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.MONGOCK;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.MONGODB;

import com.seed4j.generator.server.springboot.dbmigration.mongock.application.MongockApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MongockModuleConfiguration {

  @Bean
  JHipsterModuleResource mongockModule(MongockApplicationService mongock) {
    return JHipsterModuleResource.builder()
      .slug(MONGOCK)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database Migration", "Add Mongock")
      .organization(JHipsterModuleOrganization.builder().addDependency(MONGODB).build())
      .tags("server", "spring", "spring-boot", "database", "migration", "mongo-db")
      .factory(mongock::buildModule);
  }
}
