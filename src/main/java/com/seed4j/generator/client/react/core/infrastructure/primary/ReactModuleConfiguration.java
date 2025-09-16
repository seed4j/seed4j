package com.seed4j.generator.client.react.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.PRETTIER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.REACT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.TYPESCRIPT;

import com.seed4j.generator.client.react.core.application.ReactApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReactModuleConfiguration {

  public static final String REACT = "react";

  @Bean
  Seed4JModuleResource reactModule(ReactApplicationService react) {
    return Seed4JModuleResource.builder()
      .slug(REACT_CORE)
      .propertiesDefinition(properties())
      .apiDoc("Frontend - React", "Add React+Vite with minimal CSS")
      .organization(Seed4JModuleOrganization.builder().feature(CLIENT_CORE).addDependency(TYPESCRIPT).addDependency(PRETTIER).build())
      .tags("client", REACT)
      .factory(react::buildModule);
  }

  private Seed4JModulePropertiesDefinition properties() {
    return Seed4JModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build();
  }
}
