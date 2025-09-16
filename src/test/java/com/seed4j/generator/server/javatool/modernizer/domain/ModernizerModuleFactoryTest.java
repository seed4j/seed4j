package com.seed4j.generator.server.javatool.modernizer.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.gradleBuildFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.gradleLibsVersionFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class ModernizerModuleFactoryTest {

  private final ModernizerModuleFactory factory = new ModernizerModuleFactory();

  @Test
  void shouldBuildModuleForMaven() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
                <plugin>
                  <groupId>org.gaul</groupId>
                  <artifactId>modernizer-maven-plugin</artifactId>
                  <version>${modernizer-maven-plugin.version}</version>
                  <executions>
                    <execution>
                      <id>modernizer</id>
                      <phase>verify</phase>
                      <goals>
                        <goal>modernizer</goal>
                      </goals>
                    </execution>
                  </executions>
                  <configuration>
                    <javaVersion>${java.version}</javaVersion>
                    <failOnViolations>true</failOnViolations>
                  </configuration>
                </plugin>\
        """
      )
      .containing(
        """
              <plugin>
                <groupId>org.gaul</groupId>
                <artifactId>modernizer-maven-plugin</artifactId>
              </plugin>\
        """
      );
  }

  @Test
  void shouldBuildModuleForGradle() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, gradleBuildFile(), gradleLibsVersionFile())
      .hasFile("gradle/libs.versions.toml")
      .containing(
        """
        \t[plugins.modernizer]
        \t\tid = "com.github.andygoossens.modernizer"

        \t\t[plugins.modernizer.version]
        \t\t\tref = "modernizer"
        """
      )
      .and()
      .hasFile("build.gradle.kts")
      .containing(
        """
          alias(libs.plugins.modernizer)
          // seed4j-needle-gradle-plugins
        """
      )
      .containing(
        """
        modernizer {
          failOnViolations = true
          includeTestClasses = true
        }
        """
      );
  }
}
