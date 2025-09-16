package com.seed4j.generator.ci.renovate.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.RENOVATE;

import com.seed4j.generator.ci.renovate.application.RenovateApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RenovateModuleConfiguration {

  private static final String CI_TAG = "ci";
  private static final String RENOVATE_TAG = "renovate";

  @Bean
  Seed4JModuleResource renovateModule(RenovateApplicationService renovate) {
    return Seed4JModuleResource.builder()
      .slug(RENOVATE)
      .withoutProperties()
      .apiDoc("Dependencies updates", "Add Renovate for automatic dependency updates")
      .organization(Seed4JModuleOrganization.builder().feature(Seed4JCoreFeatureSlug.DEPENDENCIES_UPDATES).build())
      .tags(CI_TAG, RENOVATE_TAG)
      .factory(renovate::buildModule);
  }
}
