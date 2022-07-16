package tech.jhipster.lite.generator.server.springboot.cucumberauthentication.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.cucumberauthentication.application.CucumberAuthenticationApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class CucumberAuthenticationModuleConfiguration {

  @Bean
  JHipsterModuleResource cucumberAuthenticationModule(CucumberAuthenticationApplicationService cucumberAuthentications) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/component-tests/cucumber-authentication")
      .slug("cucumber-authentication")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Component Tests", "Add authentication steps for cucumber"))
      .tags("server", "spring", "spring-boot", "test")
      .factory(cucumberAuthentications::buildModule);
  }
}
