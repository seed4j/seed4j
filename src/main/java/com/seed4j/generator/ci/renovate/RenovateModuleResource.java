package com.seed4j.generator.ci.renovate;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.RENOVATE;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;
import com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug;

@AutoService(Seed4JModuleResourceProvider.class)
public class RenovateModuleResource implements Seed4JModuleResourceProvider {

  private static final String CI_TAG = "ci";
  private static final String RENOVATE_TAG = "renovate";

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(RENOVATE)
      .withoutProperties()
      .apiDoc("Dependencies updates", "Add Renovate for automatic dependency updates")
      .organization(Seed4JModuleOrganization.builder().feature(Seed4JCoreFeatureSlug.DEPENDENCIES_UPDATES).build())
      .tags(CI_TAG, RENOVATE_TAG)
      .factory(new RenovateModuleFactory()::buildModule);
  }
}
