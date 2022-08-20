package tech.jhipster.lite.generator.init.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertyDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class InitModuleConfiguration {

  @Bean
  JHipsterModuleResource initModule(InitApplicationService inits) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/inits/full")
      .slug("init")
      .propertiesDefinition(initPropertiesDefinition())
      .apiDoc(new JHipsterModuleApiDoc("Init", "Init project"))
      .organization(organization())
      .tags("server", "init")
      .factory(inits::buildFullInitModule);
  }

  @Bean
  JHipsterModuleResource initMinimalModule(InitApplicationService inits) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/inits/minimal")
      .slug("init-minimal")
      .propertiesDefinition(initPropertiesDefinition())
      .apiDoc(new JHipsterModuleApiDoc("Init", "Init minimal project"))
      .organization(organization())
      .tags("server", "init")
      .factory(inits::buildMinimalInitModule);
  }

  private JHipsterModulePropertiesDefinition initPropertiesDefinition() {
    return JHipsterModulePropertiesDefinition
      .builder()
      .addProjectBaseName()
      .addProjectName()
      .add(
        JHipsterModulePropertyDefinition
          .optionalStringProperty("endOfLine")
          .description("Type of line break (lf or crlf)")
          .example("lf")
          .order(200)
          .build()
      )
      .build();
  }

  private JHipsterModuleOrganization organization() {
    return JHipsterModuleOrganization.builder().feature("startup").addModuleDependency("git-init").build();
  }
}
