package com.seed4j.generator.client.angular.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.INIT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.PRETTIER;

import com.seed4j.generator.client.angular.core.application.AngularApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AngularModuleConfiguration {

  @Bean
  Seed4JModuleResource angularModule(AngularApplicationService angular) {
    return Seed4JModuleResource.builder()
      .slug(ANGULAR_CORE)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addProjectName().addNodePackageManager().build()
      )
      .apiDoc("Frontend - Angular", "Add Angular + Angular CLI")
      .organization(Seed4JModuleOrganization.builder().feature(CLIENT_CORE).addDependency(INIT).addDependency(PRETTIER).build())
      .tags("client", "angular")
      .factory(angular::buildModule);
  }
}
