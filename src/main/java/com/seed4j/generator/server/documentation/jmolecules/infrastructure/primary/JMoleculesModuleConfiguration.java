package com.seed4j.generator.server.documentation.jmolecules.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JMOLECULES;

import com.seed4j.generator.server.documentation.jmolecules.application.JMoleculesApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.shared.slug.domain.JHLiteFeatureSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JMoleculesModuleConfiguration {

  @Bean
  SeedModuleResource jMoleculesModule(JMoleculesApplicationService jMolecules) {
    return SeedModuleResource.builder()
      .slug(JMOLECULES)
      .withoutProperties()
      .apiDoc(
        "Documentation",
        "Add support for jMolecules documentation annotations based on DDD patterns such as @BoundedContext, @ValueObject, @Entity, @AggregateRoot..."
      )
      .organization(SeedModuleOrganization.builder().addDependency(JHLiteFeatureSlug.JAVA_BUILD_TOOL).build())
      .tags("server", "documentation")
      .factory(jMolecules::buildModule);
  }
}
