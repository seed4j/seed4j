package tech.jhipster.lite.generator.server.springboot.mvc.dummy.feature.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.dummy.feature.application.DummyApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class DummyFeatureModuleConfiguration {

  @Bean
  JHipsterModuleResource dummyFeatureModule(DummyApplicationService dummy) {
    return JHipsterModuleResource
      .builder()
      .slug(DUMMY_FEATURE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - MVC", "Add Dummy context with some APIs")
      .organization(
        JHipsterModuleOrganization
          .builder()
          .addDependency(CUCUMBER_AUTHENTICATION)
          .addDependency(SPRING_SERVER)
          .addDependency(SPRINGDOC)
          .addDependency(JAVA_BASE)
          .build()
      )
      .tags("server")
      .factory(dummy::buildModule);
  }
}
