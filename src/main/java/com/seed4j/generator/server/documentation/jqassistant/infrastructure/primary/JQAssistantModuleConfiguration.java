package com.seed4j.generator.server.documentation.jqassistant.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JMOLECULES;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JQASSISTANT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JQASSISTANT_JMOLECULES;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JQASSISTANT_SPRING;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;

import com.seed4j.generator.server.documentation.jqassistant.application.JQAssistantApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JQAssistantModuleConfiguration {

  private static final String DOCUMENTATION = "Documentation";
  private static final String SERVER_TAG = "server";
  private static final String DOCUMENTATION_TAG = "documentation";

  @Bean
  Seed4JModuleResource jQAssistantModule(JQAssistantApplicationService jqassistant) {
    return Seed4JModuleResource.builder()
      .slug(JQASSISTANT)
      .withoutProperties()
      .apiDoc(DOCUMENTATION, "Setup jQAssistant for documentation and analysis of the project")
      .organization(Seed4JModuleOrganization.builder().addDependency(Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL).build())
      .tags(SERVER_TAG, DOCUMENTATION_TAG)
      .factory(jqassistant::buildModule);
  }

  @Bean
  Seed4JModuleResource jQAssistantJMoleculesModule(JQAssistantApplicationService jqassistant) {
    return Seed4JModuleResource.builder()
      .slug(JQASSISTANT_JMOLECULES)
      .withoutProperties()
      .apiDoc(DOCUMENTATION, "Add jMolecules support for jQAssistant")
      .organization(Seed4JModuleOrganization.builder().addDependency(JQASSISTANT).addDependency(JMOLECULES).build())
      .tags(SERVER_TAG, DOCUMENTATION_TAG)
      .factory(jqassistant::buildJMoleculesModule);
  }

  @Bean
  Seed4JModuleResource jQAssistantSpringModule(JQAssistantApplicationService jqassistant) {
    return Seed4JModuleResource.builder()
      .slug(JQASSISTANT_SPRING)
      .withoutProperties()
      .apiDoc(DOCUMENTATION, "Add Spring support for jQAssistant")
      .organization(Seed4JModuleOrganization.builder().addDependency(JQASSISTANT).addDependency(SPRING_BOOT).build())
      .tags(SERVER_TAG, DOCUMENTATION_TAG)
      .factory(jqassistant::buildSpringModule);
  }
}
