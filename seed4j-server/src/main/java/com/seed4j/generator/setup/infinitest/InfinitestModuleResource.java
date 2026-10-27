package com.seed4j.generator.setup.infinitest;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.INFINITEST_FILTERS;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class InfinitestModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(INFINITEST_FILTERS)
      .withoutProperties()
      .apiDoc("Development environment", "Add filter for infinitest, the continuous test runner")
      .standalone()
      .tags("server", "init", "test")
      .factory(new InfinitestModuleFactory()::buildModule);
  }
}
