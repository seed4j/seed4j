package com.seed4j.generator.server.springboot.async.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT_ASYNC;

import com.seed4j.generator.server.springboot.async.application.SpringBootAsyncApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringBootAsyncModuleConfiguration {

  @Bean
  SeedModuleResource springBootAsyncModule(SpringBootAsyncApplicationService springBootAsync) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_ASYNC)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Tools", "Add asynchronous execution and scheduling configuration")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags("server", "spring", "spring-boot", "async")
      .factory(springBootAsync::buildModule);
  }
}
