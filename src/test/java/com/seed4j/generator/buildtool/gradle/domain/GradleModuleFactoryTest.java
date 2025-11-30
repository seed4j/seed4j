package com.seed4j.generator.buildtool.gradle.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModule;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class GradleModuleFactoryTest {

  private static final GradleModuleFactory factory = new GradleModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myApp")
      .build();

    Seed4JModule module = factory.buildGradleModule(properties);

    assertThatModule(module)
      .hasFile(".gitignore")
      .containing(
        """
        # Gradle
        /.gradle/
        /build/
        /buildSrc/.gradle/
        /buildSrc/build/\
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
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildGradleWrapperModule(properties);

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
