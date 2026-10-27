package com.seed4j.generator.client.react.security.jwt;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.REACT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.REACT_JWT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class ReactJwtModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(REACT_JWT)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("Frontend - React", "Add JWT Login React")
      .organization(Seed4JModuleOrganization.builder().addDependency(REACT_CORE).build())
      .tags("client", "react", "jwt")
      .factory(new ReactJwtModuleFactory()::buildModule);
  }
}
