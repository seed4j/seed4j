package tech.jhipster.lite.generator.server.springboot.devtools.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.devtools.application.DevToolsApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class DevToolsModuleConfiguration {

  @Bean
  JHipsterModuleResource devTools(DevToolsApplicationService devtools) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/technical-tools/devtools")
      .slug("springboot-devtools")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Tools", "Add spring boot tools."))
      .tags("server", "spring", "spring-boot", "devtools")
      .factory(devtools::buildModule);
  }
}
