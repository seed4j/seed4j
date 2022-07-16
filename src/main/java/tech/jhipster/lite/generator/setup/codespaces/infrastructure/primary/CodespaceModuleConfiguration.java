package tech.jhipster.lite.generator.setup.codespaces.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.setup.codespaces.application.CodespacesApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertyDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class CodespaceModuleConfiguration {

  @Bean
  JHipsterModuleResource codespaceModule(CodespacesApplicationService codespaces) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/developer-tools/codespaces")
      .slug("github-codespaces")
      .propertiesDefinition(propertiesDefinition())
      .apiDoc(new JHipsterModuleApiDoc("Codespaces", "Init GitHub Codespaces configuration files"))
      .tags("setup")
      .factory(codespaces::buildModule);
  }

  private JHipsterModulePropertiesDefinition propertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder().add(serverPort()).build();
  }

  private JHipsterModulePropertyDefinition serverPort() {
    return JHipsterModulePropertyDefinition
      .optionalIntegerProperty("serverPort")
      .description("Application server port")
      .example("8080")
      .order(200)
      .build();
  }
}
