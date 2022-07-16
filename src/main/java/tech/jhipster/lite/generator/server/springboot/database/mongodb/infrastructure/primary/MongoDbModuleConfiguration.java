package tech.jhipster.lite.generator.server.springboot.database.mongodb.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.database.mongodb.application.MongodbApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class MongoDbModuleConfiguration {

  @Bean
  JHipsterModuleResource mongoDbModule(MongodbApplicationService mongoDb) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/databases/mongodb")
      .slug("mongodb")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Database", "Add MongoDB drivers and dependencies, with testcontainers"))
      .factory(mongoDb::buildModule);
  }
}
