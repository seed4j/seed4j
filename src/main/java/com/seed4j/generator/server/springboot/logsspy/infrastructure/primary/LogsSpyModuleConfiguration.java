package com.seed4j.generator.server.springboot.logsspy.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.LOGS_SPY;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT;

import com.seed4j.generator.server.springboot.logsspy.application.LogsSpyApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
