package com.seed4j.generator.client.angular.admin.health.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.ANGULAR_HEALTH;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_ACTUATOR;

import com.seed4j.generator.client.angular.admin.health.application.AngularHealthApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AngularHealthModuleConfiguration {

  @Bean
  JHipsterModuleResource angularHealthModule(AngularHealthApplicationService angularHealth) {
    return JHipsterModuleResource.builder()
      .slug(ANGULAR_HEALTH)
      .withoutProperties()
      .apiDoc("Frontend - Angular", "Angular Health")
      .organization(JHipsterModuleOrganization.builder().addDependency(ANGULAR_CORE).addDependency(SPRING_BOOT_ACTUATOR).build())
      .tags("client", "angular", "health")
      .factory(angularHealth::buildModule);
  }
}
