package com.seed4j.generator.setup.infinitest.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.INFINITEST_FILTERS;

import com.seed4j.generator.setup.infinitest.application.InfinitestApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InfinitestModuleConfiguration {

  @Bean
  Seed4JModuleResource infinitestModule(InfinitestApplicationService infinitest) {
    return Seed4JModuleResource.builder()
      .slug(INFINITEST_FILTERS)
      .withoutProperties()
      .apiDoc("Development environment", "Add filter for infinitest, the continuous test runner")
      .standalone()
      .tags("server", "init", "test")
      .factory(infinitest::buildModule);
  }
}
