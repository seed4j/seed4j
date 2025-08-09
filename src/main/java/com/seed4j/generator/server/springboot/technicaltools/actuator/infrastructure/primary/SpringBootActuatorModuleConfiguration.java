package com.seed4j.generator.server.springboot.technicaltools.actuator.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_ACTUATOR;

import com.seed4j.generator.server.springboot.technicaltools.actuator.application.SpringBootActuatorApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringBootActuatorModuleConfiguration {

  @Bean
  SeedModuleResource springBootActuatorModule(SpringBootActuatorApplicationService springBootActuator) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_ACTUATOR)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot", "Add Spring Boot Actuator to the project")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_SERVER).build())
      .tags("server", "spring", "spring-boot")
      .factory(springBootActuator::buildModule);
  }
}
