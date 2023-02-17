package tech.jhipster.lite.generator.buildtool.gradle.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class GradleModuleFactory {

  private static final JHipsterSource SOURCE = from("buildtool/gradle");

  public JHipsterModule buildGradleModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return gradleWrapperModulesFiles(properties)
      .context()
        .put("dasherizedBaseName", properties.projectBaseName().kebabCase())
        .and()
      .files()
        .batch(SOURCE, to("."))
          .addTemplate("build.gradle.kts")
          .addTemplate("settings.gradle.kts")
          .and()
        .batch(SOURCE.append("gradle"), to("gradle"))
          .addFile("libs.versions.toml")
          .and()
        .and()
      .build();
    //@formatter:on
  }

  public JHipsterModule buildGradleWrapperModule(JHipsterModuleProperties properties) {
    return gradleWrapperModulesFiles(properties).build();
  }

  private static JHipsterModuleBuilder gradleWrapperModulesFiles(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .batch(SOURCE.append("gradle/wrapper"), to("gradle/wrapper"))
          .addFile("gradle-wrapper.properties")
          .addFile("gradle-wrapper.jar")
          .and()
        .addExecutable(SOURCE.file("gradlew"), to("gradlew"))
        .addExecutable(SOURCE.file("gradlew.bat"), to("gradlew.bat"))
        .and();
    //@formatter:on
  }
}
