package com.seed4j.generator.buildtool.gradle.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModule;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class GradleModuleFactoryTest {

  private static final GradleModuleFactory factory = new GradleModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myApp")
      .build();

    JHipsterModule module = factory.buildGradleModule(properties);

    assertThatModule(module)
      .hasFile(".gitignore")
      .containing(
        """
        # Gradle
        /.gradle/
        /build/
        ./buildSrc/.gradle/
        ./buildSrc/build/\
        """
      )
      .and()
      .hasFile("build.gradle.kts")
      .containing("group = \"com.seed4j.growth\"")
      .containing("testImplementation(libs.junit.engine)")
      .containing("testImplementation(libs.junit.params)")
      .containing("testImplementation(libs.assertj)")
      .containing("testImplementation(libs.mockito)")
      .and()
      .hasFile("settings.gradle.kts")
      .containing("my-app")
      .and()
      .hasFile("gradle.properties")
      .and()
      .hasFile("gradle/verification-metadata.xml")
      .and()
      .hasFile("gradle/libs.versions.toml")
      .containing("junit-jupiter = \"")
      .containing("assertj = \"")
      .containing("mockito = \"")
      .containing("[libraries.junit-engine]")
      .containing("[libraries.junit-params]")
      .containing("[libraries.assertj]")
      .containing("[libraries.mockito]");
  }

  @Test
  void shouldBuildGradleWrapperModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildGradleWrapperModule(properties);

    assertThatModuleWithFiles(module)
      .hasFile(".gitignore")
      .containing(
        """
        # Gradle Wrapper
        !gradle/wrapper/gradle-wrapper.jar\
        """
      )
      .and()
      .hasExecutableFiles("gradlew", "gradlew.bat")
      .hasPrefixedFiles("gradle/wrapper", "gradle-wrapper.jar", "gradle-wrapper.properties");
  }
}
