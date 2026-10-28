package com.seed4j.generator.typescript.core;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.INIT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.PRETTIER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.TYPESCRIPT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class TypescriptModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(TYPESCRIPT)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("Typescript", "Init Typescript project")
      .organization(Seed4JModuleOrganization.builder().addDependency(INIT).addDependency(PRETTIER).build())
      .tags("typescript")
      .factory(new TypescriptModuleFactory(services)::buildModule);
  }

  private static Seed4JModulePropertiesDefinition propertiesDefinition() {
    return Seed4JModulePropertiesDefinition.builder().addNodePackageManager().build();
  }
}
