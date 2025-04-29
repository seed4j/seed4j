package tech.jhipster.lite.generator.server.springboot.logsspy.infrastructure.primary;

import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.LOGS_SPY;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.logsspy.application.LogsSpyApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class LogsSpyModuleConfiguration {

  @Bean
  JHipsterModuleResource logSpyModule(LogsSpyApplicationService logsSpy) {
    return JHipsterModuleResource.builder()
      .slug(LOGS_SPY)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc("Spring Boot", "Add LogsSpy JUnit5 extension to project")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags("server", "test", "logback", "junit-extension")
      .factory(logsSpy::buildModule);
  }
}
