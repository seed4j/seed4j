package com.seed4j.generator.client.tools.archunitts;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CLIENT_CORE;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.HEXAGONAL_ARCHITECTURE_TESTS;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ARCH_UNIT_TS;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class ArchUnitTsModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(ARCH_UNIT_TS)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().build())
      .apiDoc("Frontend", "Add Arch unit ts")
      .organization(Seed4JModuleOrganization.builder().feature(HEXAGONAL_ARCHITECTURE_TESTS).addDependency(CLIENT_CORE).build())
      .tags("client", "test")
      .factory(new ArchUnitTsModuleFactory()::buildModule);
  }
}
