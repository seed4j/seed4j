package com.seed4j.generator.server.javatool.jacoco.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.CODE_COVERAGE_JAVA;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JACOCO;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.JACOCO_WITH_MIN_COVERAGE_CHECK;

import com.seed4j.generator.server.javatool.jacoco.application.JacocoApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JacocoModuleConfiguration {

  @Bean
  JHipsterModuleResource jacocoModule(JacocoApplicationService jacoco) {
    return JHipsterModuleResource.builder()
      .slug(JACOCO)
      .withoutProperties()
      .apiDoc("Java", "Add JaCoCo for code coverage reporting")
      .organization(JHipsterModuleOrganization.builder().feature(CODE_COVERAGE_JAVA).addDependency(JAVA_BUILD_TOOL).build())
      .tags("server", "tools", "coverage")
      .factory(jacoco::buildJacocoModule);
  }

  @Bean
  JHipsterModuleResource jacocoWithMinCoverageCheckModule(JacocoApplicationService jacoco) {
    return JHipsterModuleResource.builder()
      .slug(JACOCO_WITH_MIN_COVERAGE_CHECK)
      .withoutProperties()
      .apiDoc("Java", "Add JaCoCo for code coverage reporting and 100% coverage check")
      .organization(JHipsterModuleOrganization.builder().feature(CODE_COVERAGE_JAVA).addDependency(JAVA_BUILD_TOOL).build())
      .tags("server", "tools", "coverage")
      .factory(jacoco::buildJacocoWithMinCoverageCheckModule);
  }
}
