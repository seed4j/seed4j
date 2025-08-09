package com.seed4j.generator.buildtool.gradle.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.javaDependency;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.to;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class GradleModuleFactory {

  private static final SeedSource SOURCE = from("buildtool/gradle");

  public JHipsterModule buildGradleModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("dasherizedBaseName", properties.projectBaseName().kebabCase())
        .and()
      .gitIgnore()
        .comment("Gradle")
        .pattern("/.gradle/")
        .pattern("/build/")
        .pattern("./buildSrc/.gradle/")
        .pattern("./buildSrc/build/")
        .and()
      .files()
        .batch(SOURCE, to("."))
          .addTemplate("build.gradle.kts")
          .addTemplate("settings.gradle.kts")
          .addTemplate("gradle.properties")
          .and()
        .batch(SOURCE.append("gradle"), to("gradle"))
          .addFile("libs.versions.toml")
          .addTemplate("verification-metadata.xml")
      .and()
        .and()
      .javaDependencies()
        .addDependency(junitEngineDependency())
        .addDependency(junitParamsDependency())
        .addDependency(assertjDependency())
        .addDependency(mockitoDependency())
        .and()
      .build();
    // @formatter:on
  }

  private static JavaDependency junitEngineDependency() {
    return javaDependency()
      .groupId("org.junit.jupiter")
      .artifactId("junit-jupiter-engine")
      .versionSlug("junit-jupiter.version")
      .dependencySlug("junit-engine")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private static JavaDependency junitParamsDependency() {
    return javaDependency()
      .groupId("org.junit.jupiter")
      .artifactId("junit-jupiter-params")
      .versionSlug("junit-jupiter.version")
      .dependencySlug("junit-params")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private static JavaDependency assertjDependency() {
    return javaDependency()
      .groupId("org.assertj")
      .artifactId("assertj-core")
      .versionSlug("assertj.version")
      .dependencySlug("assertj")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private static JavaDependency mockitoDependency() {
    return javaDependency()
      .groupId("org.mockito")
      .artifactId("mockito-junit-jupiter")
      .versionSlug("mockito.version")
      .dependencySlug("mockito")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  public JHipsterModule buildGradleWrapperModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .gitIgnore()
        .comment("Gradle Wrapper")
        .pattern("!gradle/wrapper/gradle-wrapper.jar")
        .and()
      .files()
        .batch(SOURCE.append("gradle/wrapper"), to("gradle/wrapper"))
          .addFile("gradle-wrapper.properties")
          .addFile("gradle-wrapper.jar")
          .and()
        .addExecutable(SOURCE.file("gradlew"), to("gradlew"))
        .addExecutable(SOURCE.file("gradlew.bat"), to("gradlew.bat"))
        .and()
      .build();
    // @formatter:on
  }
}
