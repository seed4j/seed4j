package tech.jhipster.lite.generator.server.springboot.mvc.dummy.cassandrapersistence.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.DUMMY_PERSISTENCE;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.dummy.cassandrapersistence.application.DummyCassandraPersistenceApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class DummyCassandraPersistenceModuleConfiguration {

  @Bean
  JHipsterModuleResource dummyCassandraPersistenceModule(
    DummyCassandraPersistenceApplicationService dummyCassandraPersistenceApplicationService
  ) {
    return JHipsterModuleResource
      .builder()
      .slug(DUMMY_CASSANDRA_PERSISTENCE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Spring Boot - MVC", "Add Cassandra persistence for dummy feature")
      .organization(
        JHipsterModuleOrganization
          .builder()
          .feature(DUMMY_PERSISTENCE)
          .addDependency(DUMMY_FEATURE)
          .addDependency(CASSANDRA_MIGRATION)
          .build()
      )
      .tags("server")
      .factory(dummyCassandraPersistenceApplicationService::buildModule);
  }
}
