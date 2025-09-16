package com.seed4j.generator.server.springboot.logsspy.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LOGS_SPY;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;

import com.seed4j.generator.server.springboot.logsspy.application.LogsSpyApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogsSpyModuleConfiguration {

  @Bean
  Seed4JModuleResource logSpyModule(LogsSpyApplicationService logsSpy) {
    return Seed4JModuleResource.builder()
      .slug(LOGS_SPY)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc("Spring Boot", "Add LogsSpy JUnit5 extension to project")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags("server", "test", "logback", "junit-extension")
      .factory(logsSpy::buildModule);
  }
}
