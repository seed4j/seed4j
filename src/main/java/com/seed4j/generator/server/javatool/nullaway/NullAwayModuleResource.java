package com.seed4j.generator.server.javatool.nullaway;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;

@AutoService(Seed4JModuleResourceProvider.class)
public class NullAwayModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.NULLAWAY)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().build())
      .apiDoc("Java", "Add NullAway to check nullness of JSpecify annotated code at compile time")
      .organization(Seed4JModuleOrganization.builder().addDependency(Seed4JCoreModuleSlug.JAVA_BASE).build())
      .tags("server", "quality")
      .factory(new NullAwayModuleFactory()::buildModule);
  }
}
