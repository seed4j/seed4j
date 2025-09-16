package com.seed4j.generator.server.javatool.jacoco.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CODE_COVERAGE_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JACOCO;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JACOCO_WITH_MIN_COVERAGE_CHECK;

import com.seed4j.generator.server.javatool.jacoco.application.JacocoApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JacocoModuleConfiguration {

  @Bean
  Seed4JModuleResource jacocoModule(JacocoApplicationService jacoco) {
    return Seed4JModuleResource.builder()
      .slug(JACOCO)
      .withoutProperties()
      .apiDoc("Java", "Add JaCoCo for code coverage reporting")
      .organization(Seed4JModuleOrganization.builder().feature(CODE_COVERAGE_JAVA).addDependency(JAVA_BUILD_TOOL).build())
      .tags("server", "tools", "coverage")
      .factory(jacoco::buildJacocoModule);
  }

  @Bean
  Seed4JModuleResource jacocoWithMinCoverageCheckModule(JacocoApplicationService jacoco) {
    return Seed4JModuleResource.builder()
      .slug(JACOCO_WITH_MIN_COVERAGE_CHECK)
      .withoutProperties()
      .apiDoc("Java", "Add JaCoCo for code coverage reporting and 100% coverage check")
      .organization(Seed4JModuleOrganization.builder().feature(CODE_COVERAGE_JAVA).addDependency(JAVA_BUILD_TOOL).build())
      .tags("server", "tools", "coverage")
      .factory(jacoco::buildJacocoWithMinCoverageCheckModule);
  }
}
