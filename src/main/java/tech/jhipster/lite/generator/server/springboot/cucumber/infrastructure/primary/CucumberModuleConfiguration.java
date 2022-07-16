package tech.jhipster.lite.generator.server.springboot.cucumber.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.cucumber.application.CucumberApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertyDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class CucumberModuleConfiguration {

  @Bean
  JHipsterModuleResource cucumberModule(CucumberApplicationService cucumber) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/component-tests/cucumber")
      .slug("springboot-cucumber")
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition
          .builder()
          .addBasePackage()
          .addIndentation()
          .addProjectBaseName()
          .add(jpaResetPropertyDefinition())
          .build()
      )
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Component Tests", "Add cucumber integration to project"))
      .tags("server", "spring", "spring-boot", "test")
      .factory(cucumber::build);
  }

  private JHipsterModulePropertyDefinition jpaResetPropertyDefinition() {
    return JHipsterModulePropertyDefinition
      .optionalBooleanProperty("jpaReset")
      .description("Used to reset data from all JPA repositories")
      .order(500)
      .build();
  }
}
