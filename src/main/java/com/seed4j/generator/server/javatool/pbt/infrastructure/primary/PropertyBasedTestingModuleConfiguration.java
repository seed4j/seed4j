package com.seed4j.generator.server.javatool.pbt.infrastructure.primary;

import com.seed4j.generator.server.javatool.pbt.application.PropertyBasedTestingApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.shared.slug.domain.JHLiteFeatureSlug;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PropertyBasedTestingModuleConfiguration {

  @Bean
  SeedModuleResource jqwikModule(PropertyBasedTestingApplicationService propertyBasedTesting) {
    return SeedModuleResource.builder()
      .slug(JHLiteModuleSlug.JQWIK)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().build())
      .apiDoc("Advanced testing", "Add jqwik library for Property Based Testing")
      .organization(SeedModuleOrganization.builder().addDependency(JHLiteFeatureSlug.JAVA_BUILD_TOOL).build())
      .tags("server", "testing")
      .factory(propertyBasedTesting::buildModule);
  }
}
