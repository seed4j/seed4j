package tech.jhipster.lite.generator.buildtool.gradle.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class GradleModuleFactoryTest {

  private static final GradleModuleFactory factory = new GradleModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myApp")
      .build();

    JHipsterModule module = factory.buildGradleModule(properties);

    assertThatModule(module)
      .hasFile("build.gradle.kts")
      .containing("group = \"com.jhipster.test\"")
      .containing("testImplementation(libs.junit.engine)")
      .containing("testImplementation(libs.junit.params)")
      .containing("testImplementation(libs.assertj)")
      .containing("testImplementation(libs.mockito)")
      .and()
      .hasFile("settings.gradle.kts")
      .containing("my-app")
      .and()
      .hasFile("gradle/libs.versions.toml")
      .containing("junit-jupiter = \"")
      .containing("assertj = \"")
      .containing("mockito = \"")
      .containing("jacoco = \"")
      .containing("[libraries.junit-engine]")
      .containing("[libraries.junit-params]")
      .containing("[libraries.assertj]")
      .containing("[libraries.mockito]")
      .and()
      .hasExecutableFiles("gradlew", "gradlew.bat")
      .hasPrefixedFiles("gradle/wrapper", "gradle-wrapper.jar", "gradle-wrapper.properties");
  }

  @Test
  void shouldBuildGradleWrapperModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildGradleWrapperModule(properties);

    assertThatModuleWithFiles(module)
      .hasExecutableFiles("gradlew", "gradlew.bat")
      .hasPrefixedFiles("gradle/wrapper", "gradle-wrapper.jar", "gradle-wrapper.properties")
      .doNotHaveFiles("build.gradle.kts", "settings.gradle.kts", "gradle/libs.versions.toml");
  }
}
