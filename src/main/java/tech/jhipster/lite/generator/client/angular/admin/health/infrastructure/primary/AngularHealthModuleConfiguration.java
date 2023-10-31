package tech.jhipster.lite.generator.client.angular.admin.health.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.angular.admin.health.application.AngularHealthApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class AngularHealthModuleConfiguration {

  @Bean
  JHipsterModuleResource angularHealthModule(AngularHealthApplicationService angularHealth) {
    return JHipsterModuleResource
      .builder()
      .slug(ANGULAR_HEALTH)
      .withoutProperties()
      .apiDoc("Angular", "Angular Health")
      .organization(JHipsterModuleOrganization.builder().addDependency(ANGULAR_CORE).addDependency(SPRING_BOOT_ACTUATOR).build())
      .tags("client", "angular", "health")
      .factory(angularHealth::buildModule);
  }
}
