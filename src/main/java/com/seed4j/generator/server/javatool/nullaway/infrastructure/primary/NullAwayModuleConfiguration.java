package com.seed4j.generator.server.javatool.nullaway.infrastructure.primary;

import com.seed4j.generator.server.javatool.nullaway.application.NullAwayApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NullAwayModuleConfiguration {

  @Bean
  Seed4JModuleResource nullAwayModule(NullAwayApplicationService nullAway) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.NULLAWAY)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().build())
      .apiDoc("Java", "Add NullAway (via nullability-maven-plugin) to check nullness of JSpecify annotated code at compile time")
      .organization(
        Seed4JModuleOrganization.builder()
          .addDependency(Seed4JCoreModuleSlug.MAVEN_JAVA)
          .addDependency(Seed4JCoreModuleSlug.JAVA_BASE)
          .build()
      )
      .tags("server", "quality")
      .factory(nullAway::buildModule);
  }
}
