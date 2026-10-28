package com.seed4j.generator.client.react.core;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.PRETTIER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.REACT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.TYPESCRIPT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class ReactModuleResource implements Seed4JModuleResourceProvider {

  public static final String REACT = "react";

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(REACT_CORE)
      .propertiesDefinition(properties())
      .apiDoc("Frontend - React", "Add React+Vite with minimal CSS")
      .organization(Seed4JModuleOrganization.builder().feature(CLIENT_CORE).addDependency(TYPESCRIPT).addDependency(PRETTIER).build())
      .tags("client", REACT)
      .factory(new ReactModuleFactory(services)::buildModule);
  }

  private static Seed4JModulePropertiesDefinition properties() {
    return Seed4JModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build();
  }
}
