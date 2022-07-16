package tech.jhipster.lite.generator.server.springboot.async.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.async.application.SpringBootAsyncApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class SpringBootAsyncConfiguration {

  @Bean
  JHipsterModuleResource springBootAsync(SpringBootAsyncApplicationService springBootAsync) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/async")
      .slug("springboot-async")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Tools", "Add asynchronous execution and scheduling configuration"))
      .tags("server", "spring", "spring-boot", "async")
      .factory(springBootAsync::buildModule);
  }
}
