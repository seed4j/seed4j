package com.seed4j.generator.server.javatool.modernizer.infrastructure.primary;

import com.seed4j.generator.server.javatool.modernizer.application.ModernizerApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.shared.slug.domain.JHLiteFeatureSlug;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ModernizerModuleConfiguration {

  @Bean
  JHipsterModuleResource modernizerModule(ModernizerApplicationService modernizer) {
    return JHipsterModuleResource.builder()
      .slug(JHLiteModuleSlug.MODERNIZER)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().build())
      .apiDoc(
        "Java",
        "Add Modernizer build plugin for detecting uses of legacy APIs which modern Java versions supersede. These modern APIs are often more performant, safer, and idiomatic than the legacy equivalents."
      )
      .organization(JHipsterModuleOrganization.builder().addDependency(JHLiteFeatureSlug.JAVA_BUILD_TOOL).build())
      .tags("server", "quality")
      .factory(modernizer::buildModule);
  }
}
