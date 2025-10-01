package com.seed4j.generator.client.angular.tailwind.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_TAILWIND;

import com.seed4j.generator.client.angular.tailwind.application.AngularTailwindApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AngularTailwindModuleConfiguration {

  @Bean
  Seed4JModuleResource angularTailwindModule(AngularTailwindApplicationService angularTailwind) {
    return Seed4JModuleResource.builder()
      .slug(ANGULAR_TAILWIND)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().build())
      .apiDoc("Frontend - Angular", "Add Tailwind CSS to an Angular project")
      .organization(Seed4JModuleOrganization.builder().addDependency(ANGULAR_CORE).build())
      .tags("client", "angular", "tailwind")
      .factory(angularTailwind::buildModule);
  }
}
