package com.seed4j.generator.client.angular.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.ANGULAR_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.INIT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.PRETTIER;

import com.seed4j.generator.client.angular.core.application.AngularApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AngularModuleConfiguration {

  @Bean
  SeedModuleResource angularModule(AngularApplicationService angular) {
    return SeedModuleResource.builder()
      .slug(ANGULAR_CORE)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addProjectName().addNodePackageManager().build()
      )
      .apiDoc("Frontend - Angular", "Add Angular + Angular CLI")
      .organization(SeedModuleOrganization.builder().feature(CLIENT_CORE).addDependency(INIT).addDependency(PRETTIER).build())
      .tags("client", "angular")
      .factory(angular::buildModule);
  }
}
