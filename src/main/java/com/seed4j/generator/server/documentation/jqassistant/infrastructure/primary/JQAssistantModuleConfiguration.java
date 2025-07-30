package com.seed4j.generator.server.documentation.jqassistant.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JMOLECULES;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JQASSISTANT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JQASSISTANT_JMOLECULES;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JQASSISTANT_SPRING;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT;

import com.seed4j.generator.server.documentation.jqassistant.application.JQAssistantApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.shared.slug.domain.JHLiteFeatureSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JQAssistantModuleConfiguration {

  private static final String DOCUMENTATION = "Documentation";
  private static final String SERVER_TAG = "server";
  private static final String DOCUMENTATION_TAG = "documentation";

  @Bean
  JHipsterModuleResource jQAssistantModule(JQAssistantApplicationService jqassistant) {
    return JHipsterModuleResource.builder()
      .slug(JQASSISTANT)
      .withoutProperties()
      .apiDoc(DOCUMENTATION, "Setup jQAssistant for documentation and analysis of the project")
      .organization(JHipsterModuleOrganization.builder().addDependency(JHLiteFeatureSlug.JAVA_BUILD_TOOL).build())
      .tags(SERVER_TAG, DOCUMENTATION_TAG)
      .factory(jqassistant::buildModule);
  }

  @Bean
  JHipsterModuleResource jQAssistantJMoleculesModule(JQAssistantApplicationService jqassistant) {
    return JHipsterModuleResource.builder()
      .slug(JQASSISTANT_JMOLECULES)
      .withoutProperties()
      .apiDoc(DOCUMENTATION, "Add jMolecules support for jQAssistant")
      .organization(JHipsterModuleOrganization.builder().addDependency(JQASSISTANT).addDependency(JMOLECULES).build())
      .tags(SERVER_TAG, DOCUMENTATION_TAG)
      .factory(jqassistant::buildJMoleculesModule);
  }

  @Bean
  JHipsterModuleResource jQAssistantSpringModule(JQAssistantApplicationService jqassistant) {
    return JHipsterModuleResource.builder()
      .slug(JQASSISTANT_SPRING)
      .withoutProperties()
      .apiDoc(DOCUMENTATION, "Add Spring support for jQAssistant")
      .organization(JHipsterModuleOrganization.builder().addDependency(JQASSISTANT).addDependency(SPRING_BOOT).build())
      .tags(SERVER_TAG, DOCUMENTATION_TAG)
      .factory(jqassistant::buildSpringModule);
  }
}
