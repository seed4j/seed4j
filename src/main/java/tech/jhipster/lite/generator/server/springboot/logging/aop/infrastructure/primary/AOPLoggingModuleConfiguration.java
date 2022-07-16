package tech.jhipster.lite.generator.server.springboot.logging.aop.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.logging.aop.application.AopLoggingApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class AOPLoggingModuleConfiguration {

  @Bean
  JHipsterModuleResource aopLoggingModule(AopLoggingApplicationService aopLogging) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/log-tools/aop")
      .slug("aop-logging")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectName().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Logging", "Add AOP logging"))
      .tags("server", "spring", "spring-boot", "log")
      .factory(aopLogging::buildModule);
  }
}
