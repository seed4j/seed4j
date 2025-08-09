package com.seed4j.generator.client.angular.admin.health.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.ANGULAR_HEALTH;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_ACTUATOR;

import com.seed4j.generator.client.angular.admin.health.application.AngularHealthApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AngularHealthModuleConfiguration {

  @Bean
  SeedModuleResource angularHealthModule(AngularHealthApplicationService angularHealth) {
    return SeedModuleResource.builder()
      .slug(ANGULAR_HEALTH)
      .withoutProperties()
      .apiDoc("Frontend - Angular", "Angular Health")
      .organization(SeedModuleOrganization.builder().addDependency(ANGULAR_CORE).addDependency(SPRING_BOOT_ACTUATOR).build())
      .tags("client", "angular", "health")
      .factory(angularHealth::buildModule);
  }
}
