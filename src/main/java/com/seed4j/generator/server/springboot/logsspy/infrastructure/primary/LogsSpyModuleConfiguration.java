package com.seed4j.generator.server.springboot.logsspy.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.LOGS_SPY;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT;

import com.seed4j.generator.server.springboot.logsspy.application.LogsSpyApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogsSpyModuleConfiguration {

  @Bean
  SeedModuleResource logSpyModule(LogsSpyApplicationService logsSpy) {
    return SeedModuleResource.builder()
      .slug(LOGS_SPY)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc("Spring Boot", "Add LogsSpy JUnit5 extension to project")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags("server", "test", "logback", "junit-extension")
      .factory(logsSpy::buildModule);
  }
}
