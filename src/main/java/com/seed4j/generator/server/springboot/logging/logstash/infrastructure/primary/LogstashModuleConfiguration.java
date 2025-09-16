package com.seed4j.generator.server.springboot.logging.logstash.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.LOGSTASH;

import com.seed4j.generator.server.springboot.logging.logstash.application.LogstashApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogstashModuleConfiguration {

  @Bean
  Seed4JModuleResource logstashModule(LogstashApplicationService logstash) {
    return Seed4JModuleResource.builder()
      .slug(LOGSTASH)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot", "Add Logstash TCP appender")
      .organization(Seed4JModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "logstash", "spring", "spring-boot")
      .factory(logstash::buildModule);
  }
}
