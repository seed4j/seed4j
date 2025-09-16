package com.seed4j.generator.client.angular.admin.health.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_HEALTH;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_ACTUATOR;

import com.seed4j.generator.client.angular.admin.health.application.AngularHealthApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AngularHealthModuleConfiguration {

  @Bean
  Seed4JModuleResource angularHealthModule(AngularHealthApplicationService angularHealth) {
    return Seed4JModuleResource.builder()
      .slug(ANGULAR_HEALTH)
      .withoutProperties()
      .apiDoc("Frontend - Angular", "Angular Health")
      .organization(Seed4JModuleOrganization.builder().addDependency(ANGULAR_CORE).addDependency(SPRING_BOOT_ACTUATOR).build())
      .tags("client", "angular", "health")
      .factory(angularHealth::buildModule);
  }
}
