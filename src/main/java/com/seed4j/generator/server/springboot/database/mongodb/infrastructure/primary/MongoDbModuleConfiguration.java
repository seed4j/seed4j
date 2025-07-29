package com.seed4j.generator.server.springboot.database.mongodb.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.MONGODB;

import com.seed4j.generator.server.springboot.database.mongodb.application.MongoDbApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MongoDbModuleConfiguration {

  @Bean
  JHipsterModuleResource mongoDbModule(MongoDbApplicationService mongoDb) {
    return JHipsterModuleResource.builder()
      .slug(MONGODB)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database", "Add MongoDB drivers and dependencies, with testcontainers")
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(mongoDb::buildModule);
  }
}
