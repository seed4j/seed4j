package tech.jhipster.lite.generator.server.springboot.cucumbercommon.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public final class CucumbersModules {

  private static final String CUCUMBER_GROUP_ID = "io.cucumber";
  private static final String CUCUMBER_VERSION = "cucumber.version";

  private CucumbersModules() {}

  public static JHipsterModuleBuilder cucumberModuleBuilder(JHipsterModuleProperties properties) {
    return moduleBuilder(properties)
      .javaDependencies()
      .addDependency(cucumberJunitDependency())
      .addDependency(cucumberJavaDependency())
      .addDependency(cucumberSpringDependency())
      .addDependency(junitVintageDependency())
      .and();
  }

  private static JavaDependency cucumberJunitDependency() {
    return javaDependency()
      .groupId(CUCUMBER_GROUP_ID)
      .artifactId("cucumber-junit")
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

  private static JavaDependency junitVintageDependency() {
    return javaDependency().groupId("org.junit.vintage").artifactId("junit-vintage-engine").scope(JavaDependencyScope.TEST).build();
  }
}
