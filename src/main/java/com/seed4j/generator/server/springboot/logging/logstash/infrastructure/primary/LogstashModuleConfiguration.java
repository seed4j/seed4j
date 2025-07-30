package com.seed4j.generator.server.springboot.logging.logstash.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.LOGSTASH;

import com.seed4j.generator.server.springboot.logging.logstash.application.LogstashApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogstashModuleConfiguration {

  @Bean
  JHipsterModuleResource logstashModule(LogstashApplicationService logstash) {
    return JHipsterModuleResource.builder()
      .slug(LOGSTASH)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot", "Add Logstash TCP appender")
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "logstash", "spring", "spring-boot")
      .factory(logstash::buildModule);
  }
}
