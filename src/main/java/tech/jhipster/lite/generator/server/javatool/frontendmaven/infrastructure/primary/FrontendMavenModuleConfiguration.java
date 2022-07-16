package tech.jhipster.lite.generator.server.javatool.frontendmaven.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.javatool.frontendmaven.application.FrontendMavenApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class FrontendMavenModuleConfiguration {

  @Bean
  JHipsterModuleResource frontendMavenModule(FrontendMavenApplicationService frontendMaven) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/developer-tools/frontend-maven-plugin")
      .slug("frontend-maven-plugin")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Frontend Maven Plugin", "Add Frontend Maven Plugin"))
      .tags("server", "tools")
      .factory(frontendMaven::buildModule);
  }
}
