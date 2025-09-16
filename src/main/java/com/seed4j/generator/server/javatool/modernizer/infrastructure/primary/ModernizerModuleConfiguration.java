package com.seed4j.generator.server.javatool.modernizer.infrastructure.primary;

import com.seed4j.generator.server.javatool.modernizer.application.ModernizerApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ModernizerModuleConfiguration {

  @Bean
  Seed4JModuleResource modernizerModule(ModernizerApplicationService modernizer) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.MODERNIZER)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().build())
      .apiDoc(
        "Java",
        "Add Modernizer build plugin for detecting uses of legacy APIs which modern Java versions supersede. These modern APIs are often more performant, safer, and idiomatic than the legacy equivalents."
      )
      .organization(Seed4JModuleOrganization.builder().addDependency(Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL).build())
      .tags("server", "quality")
      .factory(modernizer::buildModule);
  }
}
