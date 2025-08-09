package com.seed4j.generator.client.react.core.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.PRETTIER;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.REACT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.TYPESCRIPT;

import com.seed4j.generator.client.react.core.application.ReactApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReactModuleConfiguration {

  public static final String REACT = "react";

  @Bean
  SeedModuleResource reactModule(ReactApplicationService react) {
    return SeedModuleResource.builder()
      .slug(REACT_CORE)
      .propertiesDefinition(properties())
      .apiDoc("Frontend - React", "Add React+Vite with minimal CSS")
      .organization(SeedModuleOrganization.builder().feature(CLIENT_CORE).addDependency(TYPESCRIPT).addDependency(PRETTIER).build())
      .tags("client", REACT)
      .factory(react::buildModule);
  }

  private SeedModulePropertiesDefinition properties() {
    return SeedModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build();
  }
}
