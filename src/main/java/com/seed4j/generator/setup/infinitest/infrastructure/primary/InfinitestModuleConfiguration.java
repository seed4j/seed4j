package com.seed4j.generator.setup.infinitest.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.INFINITEST_FILTERS;

import com.seed4j.generator.setup.infinitest.application.InfinitestApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InfinitestModuleConfiguration {

  @Bean
  JHipsterModuleResource infinitestModule(InfinitestApplicationService infinitest) {
    return JHipsterModuleResource.builder()
      .slug(INFINITEST_FILTERS)
      .withoutProperties()
      .apiDoc("Development environment", "Add filter for infinitest, the continuous test runner")
      .standalone()
      .tags("server", "init", "test")
      .factory(infinitest::buildModule);
  }
}
