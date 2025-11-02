package com.seed4j.generator.server.javatool.checkstyle.domain;

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
class CheckstyleModuleFactoryTest {

  private final CheckstyleModuleFactory factory = new CheckstyleModuleFactory();

  @Test
  void shouldBuildModuleForMaven() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildModule(properties);

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
      .containing("<module name=\"Checker\">")
      .containing("<module name=\"TreeWalker\">")
      .containing("<module name=\"UnusedImports\" />");
  }

  @Test
  void shouldBuildModuleForGradle() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildModule(properties);

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
