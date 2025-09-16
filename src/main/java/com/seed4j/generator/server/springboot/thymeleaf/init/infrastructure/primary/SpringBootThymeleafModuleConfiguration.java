package com.seed4j.generator.server.springboot.thymeleaf.init.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_THYMELEAF;

import com.seed4j.generator.server.springboot.thymeleaf.init.application.SpringBootThymeleafApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringBootThymeleafModuleConfiguration {

  @Bean
  Seed4JModuleResource springBootThymeleafModule(SpringBootThymeleafApplicationService springBootThymeleaf) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_THYMELEAF)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Thymeleaf", "Add Spring Boot Thymeleaf to the project")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_SERVER).build())
      .tags("server", "spring", "spring-boot")
      .factory(springBootThymeleaf::buildModule);
  }
}
