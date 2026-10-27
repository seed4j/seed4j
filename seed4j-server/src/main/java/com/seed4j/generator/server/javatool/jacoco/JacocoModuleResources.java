package com.seed4j.generator.server.javatool.jacoco;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CODE_COVERAGE_JAVA;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JACOCO;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JACOCO_WITH_MIN_COVERAGE_CHECK;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class JacocoModuleResources {

  private JacocoModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Jacoco implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(JACOCO)
        .withoutProperties()
        .apiDoc("Java", "Add JaCoCo for code coverage reporting")
        .organization(Seed4JModuleOrganization.builder().feature(CODE_COVERAGE_JAVA).addDependency(JAVA_BUILD_TOOL).build())
        .tags("server", "tools", "coverage")
        .factory(new JacocoModuleFactory()::buildJacocoModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class WithMinCoverageCheck implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(JACOCO_WITH_MIN_COVERAGE_CHECK)
        .withoutProperties()
        .apiDoc("Java", "Add JaCoCo for code coverage reporting and 100% coverage check")
        .organization(Seed4JModuleOrganization.builder().feature(CODE_COVERAGE_JAVA).addDependency(JAVA_BUILD_TOOL).build())
        .tags("server", "tools", "coverage")
        .factory(new JacocoModuleFactory()::buildJacocoWithMinCoverageCheckModule);
    }
  }
}
