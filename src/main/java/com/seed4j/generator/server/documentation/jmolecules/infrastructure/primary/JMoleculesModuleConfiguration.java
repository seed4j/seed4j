package com.seed4j.generator.server.documentation.jmolecules.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JMOLECULES;

import com.seed4j.generator.server.documentation.jmolecules.application.JMoleculesApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.shared.slug.domain.JHLiteFeatureSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JMoleculesModuleConfiguration {

  @Bean
  JHipsterModuleResource jMoleculesModule(JMoleculesApplicationService jMolecules) {
    return JHipsterModuleResource.builder()
      .slug(JMOLECULES)
      .withoutProperties()
      .apiDoc(
        "Documentation",
        "Add support for jMolecules documentation annotations based on DDD patterns such as @BoundedContext, @ValueObject, @Entity, @AggregateRoot..."
      )
      .organization(JHipsterModuleOrganization.builder().addDependency(JHLiteFeatureSlug.JAVA_BUILD_TOOL).build())
      .tags("server", "documentation")
      .factory(jMolecules::buildModule);
  }
}
