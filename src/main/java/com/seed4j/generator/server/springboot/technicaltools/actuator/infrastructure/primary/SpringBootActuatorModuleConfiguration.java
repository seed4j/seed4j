package com.seed4j.generator.server.springboot.technicaltools.actuator.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_ACTUATOR;

import com.seed4j.generator.server.springboot.technicaltools.actuator.application.SpringBootActuatorApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringBootActuatorModuleConfiguration {

  @Bean
  Seed4JModuleResource springBootActuatorModule(SpringBootActuatorApplicationService springBootActuator) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_ACTUATOR)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot", "Add Spring Boot Actuator to the project")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_SERVER).build())
      .tags("server", "spring", "spring-boot")
      .factory(springBootActuator::buildModule);
  }
}
