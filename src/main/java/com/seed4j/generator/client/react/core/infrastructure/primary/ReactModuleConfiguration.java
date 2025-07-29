package com.seed4j.generator.client.react.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.PRETTIER;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.REACT_CORE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.TYPESCRIPT;

import com.seed4j.generator.client.react.core.application.ReactApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReactModuleConfiguration {

  public static final String REACT = "react";

  @Bean
  JHipsterModuleResource reactModule(ReactApplicationService react) {
    return JHipsterModuleResource.builder()
      .slug(REACT_CORE)
      .propertiesDefinition(properties())
      .apiDoc("Frontend - React", "Add React+Vite with minimal CSS")
      .organization(JHipsterModuleOrganization.builder().feature(CLIENT_CORE).addDependency(TYPESCRIPT).addDependency(PRETTIER).build())
      .tags("client", REACT)
      .factory(react::buildModule);
  }

  private JHipsterModulePropertiesDefinition properties() {
    return JHipsterModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build();
  }
}
