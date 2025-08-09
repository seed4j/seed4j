package com.seed4j.generator.server.springboot.cucumbercommon.domain;

import static com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import static com.seed4j.module.domain.JHipsterModule.javaDependency;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;

import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.properties.SeedModuleProperties;

public final class CucumbersModules {

  private static final String CUCUMBER_GROUP_ID = "io.cucumber";
  private static final String CUCUMBER_VERSION = "cucumber.version";

  private CucumbersModules() {}

  public static JHipsterModuleBuilder cucumberModuleBuilder(SeedModuleProperties properties) {
    return moduleBuilder(properties)
      .javaDependencies()
      .addDependency(cucumberJunitPlatformEngineDependency())
      .addDependency(cucumberJavaDependency())
      .addDependency(cucumberSpringDependency())
      .addDependency(junitPlatformSuiteDependency())
      .and();
  }

  private static JavaDependency cucumberJunitPlatformEngineDependency() {
    return javaDependency()
      .groupId(CUCUMBER_GROUP_ID)
      .artifactId("cucumber-junit-platform-engine")
      .versionSlug(CUCUMBER_VERSION)
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private static JavaDependency cucumberJavaDependency() {
    return javaDependency()
      .groupId(CUCUMBER_GROUP_ID)
      .artifactId("cucumber-java")
      .versionSlug(CUCUMBER_VERSION)
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private static JavaDependency cucumberSpringDependency() {
    return javaDependency()
      .groupId(CUCUMBER_GROUP_ID)
      .artifactId("cucumber-spring")
      .versionSlug(CUCUMBER_VERSION)
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private static JavaDependency junitPlatformSuiteDependency() {
    return javaDependency().groupId("org.junit.platform").artifactId("junit-platform-suite").scope(JavaDependencyScope.TEST).build();
  }
}
