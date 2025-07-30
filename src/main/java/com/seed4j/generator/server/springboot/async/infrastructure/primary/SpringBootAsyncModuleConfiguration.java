package com.seed4j.generator.server.springboot.async.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_ASYNC;

import com.seed4j.generator.server.springboot.async.application.SpringBootAsyncApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringBootAsyncModuleConfiguration {

  @Bean
  JHipsterModuleResource springBootAsyncModule(SpringBootAsyncApplicationService springBootAsync) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_ASYNC)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Tools", "Add asynchronous execution and scheduling configuration")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags("server", "spring", "spring-boot", "async")
      .factory(springBootAsync::buildModule);
  }
}
