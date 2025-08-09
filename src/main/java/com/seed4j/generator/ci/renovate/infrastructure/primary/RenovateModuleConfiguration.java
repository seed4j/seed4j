package com.seed4j.generator.ci.renovate.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.RENOVATE;

import com.seed4j.generator.ci.renovate.application.RenovateApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.shared.slug.domain.Seed4JFeatureSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RenovateModuleConfiguration {

  private static final String CI_TAG = "ci";
  private static final String RENOVATE_TAG = "renovate";

  @Bean
  SeedModuleResource renovateModule(RenovateApplicationService renovate) {
    return SeedModuleResource.builder()
      .slug(RENOVATE)
      .withoutProperties()
      .apiDoc("Dependencies updates", "Add Renovate for automatic dependency updates")
      .organization(SeedModuleOrganization.builder().feature(Seed4JFeatureSlug.DEPENDENCIES_UPDATES).build())
      .tags(CI_TAG, RENOVATE_TAG)
      .factory(renovate::buildModule);
  }
}
