package com.seed4j.generator.server.springboot.thymeleaf.init.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_THYMELEAF;

import com.seed4j.generator.server.springboot.thymeleaf.init.application.SpringBootThymeleafApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringBootThymeleafModuleConfiguration {

  @Bean
  JHipsterModuleResource springBootThymeleafModule(SpringBootThymeleafApplicationService springBootThymeleaf) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_THYMELEAF)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Thymeleaf", "Add Spring Boot Thymeleaf to the project")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_SERVER).build())
      .tags("server", "spring", "spring-boot")
      .factory(springBootThymeleaf::buildModule);
  }
}
