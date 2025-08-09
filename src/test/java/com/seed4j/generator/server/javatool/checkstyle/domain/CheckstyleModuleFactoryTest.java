package com.seed4j.generator.server.javatool.checkstyle.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.gradleBuildFile;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.gradleLibsVersionFile;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class CheckstyleModuleFactoryTest {

  private final CheckstyleModuleFactory factory = new CheckstyleModuleFactory();

  @Test
  void shouldBuildModuleForMaven() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing("<maven-checkstyle-plugin.version>")
      .containing(
        """
              <plugin>
                <artifactId>maven-checkstyle-plugin</artifactId>
                <version>${maven-checkstyle-plugin.version}</version>
                <executions>
                  <execution>
                    <id>validate</id>
                    <phase>validate</phase>
                    <goals>
                      <goal>check</goal>
                    </goals>
                  </execution>
                </executions>
                <dependencies>
                  <dependency>
                    <groupId>com.puppycrawl.tools</groupId>
                    <artifactId>checkstyle</artifactId>
                    <version>${checkstyle.version}</version>
                  </dependency>
                </dependencies>
                <configuration>
                  <configLocation>checkstyle.xml</configLocation>
                  <consoleOutput>true</consoleOutput>
                  <failsOnError>true</failsOnError>
                  <includeTestSourceDirectory>true</includeTestSourceDirectory>
                  <sourceDirectories>
                    <sourceDirectory>${project.build.sourceDirectory}</sourceDirectory>
                  </sourceDirectories>
                  <testSourceDirectories>
                    <testSourceDirectory>${project.build.testSourceDirectory}</testSourceDirectory>
                  </testSourceDirectories>
                </configuration>
              </plugin>
        """
      )
      .containing("<checkstyle.version>")
      .and()
      .hasFile("checkstyle.xml")
      .containing("<module name=\"IllegalImport\">")
      .containing("<module name=\"Checker\">")
      .containing("<module name=\"TreeWalker\">")
      .containing("<module name=\"UnusedImports\" />");
  }

  @Test
  void shouldBuildModuleForGradle() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, gradleBuildFile(), gradleLibsVersionFile())
      .hasFile("build.gradle.kts")
      .containing(
        """
          checkstyle
          // seed4j-needle-gradle-plugins
        }
        """
      )
      .containing(
        """
        checkstyle {
          configFile = rootProject.file("checkstyle.xml")
          toolVersion = libs.versions.checkstyle.get()
        }
        """
      )
      .and()
      .hasFile("gradle/libs.versions.toml")
      .containing("checkstyle = \"")
      .notContaining("maven-checkstyle-plugin");
  }
}
