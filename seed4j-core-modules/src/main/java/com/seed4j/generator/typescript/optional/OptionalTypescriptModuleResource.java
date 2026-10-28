package com.seed4j.generator.typescript.optional;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.OPTIONAL_TYPESCRIPT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.TYPESCRIPT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class OptionalTypescriptModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(OPTIONAL_TYPESCRIPT)
      .withoutProperties()
      .apiDoc("Typescript", "Add Optional class domain to project")
      .organization(Seed4JModuleOrganization.builder().addDependency(TYPESCRIPT).build())
      .tags("typescript")
      .factory(new OptionalTypescriptModuleFactory()::buildModule);
  }
}
