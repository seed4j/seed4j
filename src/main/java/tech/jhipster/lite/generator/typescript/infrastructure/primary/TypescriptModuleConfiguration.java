package tech.jhipster.lite.generator.typescript.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.typescript.application.TypescriptApplicationService;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class TypescriptModuleConfiguration {

  @Bean
  JHipsterModuleResource typescriptModule(TypescriptApplicationService typescriptApplicationService) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/javascript-languages/typescript")
      .slug("typescript")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc("Typescript", "Init Typescript project"))
      .tags("typescript")
      .factory(typescriptApplicationService::buildModule);
  }
}
