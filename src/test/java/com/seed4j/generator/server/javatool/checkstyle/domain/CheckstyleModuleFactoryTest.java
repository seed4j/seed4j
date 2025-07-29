package com.seed4j.generator.server.javatool.checkstyle.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.gradleBuildFile;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.gradleLibsVersionFile;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class CheckstyleModuleFactoryTest {

  private final CheckstyleModuleFactory factory = new CheckstyleModuleFactory();

  @Test
  void shouldBuildModuleForMaven() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

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
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, gradleBuildFile(), gradleLibsVersionFile())
      .hasFile("build.gradle.kts")
      .containing(
        """
          checkstyle
          // jhipster-needle-gradle-plugins
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
