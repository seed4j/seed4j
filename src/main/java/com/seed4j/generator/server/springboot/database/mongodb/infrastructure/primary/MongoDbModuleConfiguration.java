package com.seed4j.generator.server.springboot.database.mongodb.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.MONGODB;

import com.seed4j.generator.server.springboot.database.mongodb.application.MongoDbApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MongoDbModuleConfiguration {

  @Bean
  SeedModuleResource mongoDbModule(MongoDbApplicationService mongoDb) {
    return SeedModuleResource.builder()
      .slug(MONGODB)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database", "Add MongoDB drivers and dependencies, with testcontainers")
      .organization(SeedModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(mongoDb::buildModule);
  }
}
