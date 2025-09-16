package com.seed4j.generator.server.springboot.async.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_ASYNC;

import com.seed4j.generator.server.springboot.async.application.SpringBootAsyncApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringBootAsyncModuleConfiguration {

  @Bean
  Seed4JModuleResource springBootAsyncModule(SpringBootAsyncApplicationService springBootAsync) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_ASYNC)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Tools", "Add asynchronous execution and scheduling configuration")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags("server", "spring", "spring-boot", "async")
      .factory(springBootAsync::buildModule);
  }
}
