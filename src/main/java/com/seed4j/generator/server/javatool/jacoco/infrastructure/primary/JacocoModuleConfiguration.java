package com.seed4j.generator.server.javatool.jacoco.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CODE_COVERAGE_JAVA;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JACOCO;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JACOCO_WITH_MIN_COVERAGE_CHECK;

import com.seed4j.generator.server.javatool.jacoco.application.JacocoApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JacocoModuleConfiguration {

  @Bean
  SeedModuleResource jacocoModule(JacocoApplicationService jacoco) {
    return SeedModuleResource.builder()
      .slug(JACOCO)
      .withoutProperties()
      .apiDoc("Java", "Add JaCoCo for code coverage reporting")
      .organization(SeedModuleOrganization.builder().feature(CODE_COVERAGE_JAVA).addDependency(JAVA_BUILD_TOOL).build())
      .tags("server", "tools", "coverage")
      .factory(jacoco::buildJacocoModule);
  }

  @Bean
  SeedModuleResource jacocoWithMinCoverageCheckModule(JacocoApplicationService jacoco) {
    return SeedModuleResource.builder()
      .slug(JACOCO_WITH_MIN_COVERAGE_CHECK)
      .withoutProperties()
      .apiDoc("Java", "Add JaCoCo for code coverage reporting and 100% coverage check")
      .organization(SeedModuleOrganization.builder().feature(CODE_COVERAGE_JAVA).addDependency(JAVA_BUILD_TOOL).build())
      .tags("server", "tools", "coverage")
      .factory(jacoco::buildJacocoWithMinCoverageCheckModule);
  }
}
