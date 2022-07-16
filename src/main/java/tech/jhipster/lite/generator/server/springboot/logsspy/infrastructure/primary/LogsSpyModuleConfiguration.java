package tech.jhipster.lite.generator.server.springboot.logsspy.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.logsspy.application.LogsSpyApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class LogsSpyModuleConfiguration {

  @Bean
  JHipsterModuleResource logsSpyModule(LogsSpyApplicationService logsSpys) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/logs-spy")
      .slug("logs-spy")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot", "Add a JUnit extension to test logs"))
      .tags("server", "test")
      .factory(logsSpys::build);
  }
}
