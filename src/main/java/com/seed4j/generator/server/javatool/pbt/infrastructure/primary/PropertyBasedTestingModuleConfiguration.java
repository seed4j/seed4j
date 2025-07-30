package com.seed4j.generator.server.javatool.pbt.infrastructure.primary;

import com.seed4j.generator.server.javatool.pbt.application.PropertyBasedTestingApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.shared.slug.domain.JHLiteFeatureSlug;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PropertyBasedTestingModuleConfiguration {

  @Bean
  JHipsterModuleResource jqwikModule(PropertyBasedTestingApplicationService propertyBasedTesting) {
    return JHipsterModuleResource.builder()
      .slug(JHLiteModuleSlug.JQWIK)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().build())
      .apiDoc("Advanced testing", "Add jqwik library for Property Based Testing")
      .organization(JHipsterModuleOrganization.builder().addDependency(JHLiteFeatureSlug.JAVA_BUILD_TOOL).build())
      .tags("server", "testing")
      .factory(propertyBasedTesting::buildModule);
  }
}
