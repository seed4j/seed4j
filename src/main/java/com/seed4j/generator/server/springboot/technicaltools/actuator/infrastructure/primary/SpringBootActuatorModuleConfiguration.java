package com.seed4j.generator.server.springboot.technicaltools.actuator.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.SPRING_SERVER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_ACTUATOR;

import com.seed4j.generator.server.springboot.technicaltools.actuator.application.SpringBootActuatorApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringBootActuatorModuleConfiguration {

  @Bean
  JHipsterModuleResource springBootActuatorModule(SpringBootActuatorApplicationService springBootActuator) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_ACTUATOR)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot", "Add Spring Boot Actuator to the project")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_SERVER).build())
      .tags("server", "spring", "spring-boot")
      .factory(springBootActuator::buildModule);
  }
}
