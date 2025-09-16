package com.seed4j.generator.server.documentation.jmolecules.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JMOLECULES;

import com.seed4j.generator.server.documentation.jmolecules.application.JMoleculesApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JMoleculesModuleConfiguration {

  @Bean
  Seed4JModuleResource jMoleculesModule(JMoleculesApplicationService jMolecules) {
    return Seed4JModuleResource.builder()
      .slug(JMOLECULES)
      .withoutProperties()
      .apiDoc(
        "Documentation",
        "Add support for jMolecules documentation annotations based on DDD patterns such as @BoundedContext, @ValueObject, @Entity, @AggregateRoot..."
      )
      .organization(Seed4JModuleOrganization.builder().addDependency(Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL).build())
      .tags("server", "documentation")
      .factory(jMolecules::buildModule);
  }
}
