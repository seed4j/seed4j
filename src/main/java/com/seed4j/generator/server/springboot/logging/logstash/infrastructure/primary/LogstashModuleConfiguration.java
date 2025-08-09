package com.seed4j.generator.server.springboot.logging.logstash.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.LOGSTASH;

import com.seed4j.generator.server.springboot.logging.logstash.application.LogstashApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogstashModuleConfiguration {

  @Bean
  SeedModuleResource logstashModule(LogstashApplicationService logstash) {
    return SeedModuleResource.builder()
      .slug(LOGSTASH)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot", "Add Logstash TCP appender")
      .organization(SeedModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "logstash", "spring", "spring-boot")
      .factory(logstash::buildModule);
  }
}
