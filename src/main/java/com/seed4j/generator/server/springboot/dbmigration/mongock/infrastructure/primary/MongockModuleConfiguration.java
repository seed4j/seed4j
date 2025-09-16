package com.seed4j.generator.server.springboot.dbmigration.mongock.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MONGOCK;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MONGODB;

import com.seed4j.generator.server.springboot.dbmigration.mongock.application.MongockApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MongockModuleConfiguration {

  @Bean
  Seed4JModuleResource mongockModule(MongockApplicationService mongock) {
    return Seed4JModuleResource.builder()
      .slug(MONGOCK)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database Migration", "Add Mongock")
      .organization(Seed4JModuleOrganization.builder().addDependency(MONGODB).build())
      .tags("server", "spring", "spring-boot", "database", "migration", "mongo-db")
      .factory(mongock::buildModule);
  }
}
