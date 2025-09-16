package com.seed4j.generator.server.javatool.pbt.infrastructure.primary;

import com.seed4j.generator.server.javatool.pbt.application.PropertyBasedTestingApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PropertyBasedTestingModuleConfiguration {

  @Bean
  Seed4JModuleResource jqwikModule(PropertyBasedTestingApplicationService propertyBasedTesting) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.JQWIK)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().build())
      .apiDoc("Advanced testing", "Add jqwik library for Property Based Testing")
      .organization(Seed4JModuleOrganization.builder().addDependency(Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL).build())
      .tags("server", "testing")
      .factory(propertyBasedTesting::buildModule);
  }
}
