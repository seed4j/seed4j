package com.seed4j.generator.server.springboot.database.mongodb.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MONGODB;

import com.seed4j.generator.server.springboot.database.mongodb.application.MongoDbApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MongoDbModuleConfiguration {

  @Bean
  Seed4JModuleResource mongoDbModule(MongoDbApplicationService mongoDb) {
    return Seed4JModuleResource.builder()
      .slug(MONGODB)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database", "Add MongoDB drivers and dependencies, with testcontainers")
      .organization(Seed4JModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(mongoDb::buildModule);
  }
}
