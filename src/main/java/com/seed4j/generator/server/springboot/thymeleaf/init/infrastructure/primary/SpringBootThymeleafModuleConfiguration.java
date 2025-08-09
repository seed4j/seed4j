package com.seed4j.generator.server.springboot.thymeleaf.init.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_THYMELEAF;

import com.seed4j.generator.server.springboot.thymeleaf.init.application.SpringBootThymeleafApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringBootThymeleafModuleConfiguration {

  @Bean
  SeedModuleResource springBootThymeleafModule(SpringBootThymeleafApplicationService springBootThymeleaf) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_THYMELEAF)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Thymeleaf", "Add Spring Boot Thymeleaf to the project")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_SERVER).build())
      .tags("server", "spring", "spring-boot")
      .factory(springBootThymeleaf::buildModule);
  }
}
