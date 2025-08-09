package com.seed4j.generator.server.springboot.dbmigration.mongock.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.MONGOCK;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.MONGODB;

import com.seed4j.generator.server.springboot.dbmigration.mongock.application.MongockApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MongockModuleConfiguration {

  @Bean
  SeedModuleResource mongockModule(MongockApplicationService mongock) {
    return SeedModuleResource.builder()
      .slug(MONGOCK)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database Migration", "Add Mongock")
      .organization(SeedModuleOrganization.builder().addDependency(MONGODB).build())
      .tags("server", "spring", "spring-boot", "database", "migration", "mongo-db")
      .factory(mongock::buildModule);
  }
}
