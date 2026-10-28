package com.seed4j.generator.server.javatool.nullaway;

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
class NullAwayModuleFactoryTest {

  private final NullAwayModuleFactory factory = new NullAwayModuleFactory();

  @Test
  void shouldBuildModuleForMaven() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .notContaining("error-prone")
      .containing(
        """
                <plugin>
                  <groupId>am.ik.maven</groupId>
                  <artifactId>nullability-maven-plugin</artifactId>
                  <version>${nullability-maven-plugin.version}</version>
                  <extensions>true</extensions>
                  <executions>
                    <execution>
                      <goals>
                        <goal>configure</goal>
                      </goals>
                    </execution>
                  </executions>
                  <configuration>
                    <nullAwayVersion>${nullaway.version}</nullAwayVersion>
                    <outputDirectory>${project.basedir}/src/main/java</outputDirectory>
                    <testOutputDirectory>${project.basedir}/src/test/java</testOutputDirectory>
                  </configuration>
                </plugin>\
        """
      )
      .containing(
        """
              <plugin>
                <groupId>am.ik.maven</groupId>
                <artifactId>nullability-maven-plugin</artifactId>
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
      .containing("\tnullaway = \"")
      .containing("\terror-prone = \"")
      .containing(
        """
        \t[plugins.errorprone]
        \t\tid = "net.ltgt.errorprone"

        \t\t[plugins.errorprone.version]
        \t\t\tref = "gradle-errorprone-plugin"
        """
      )
      .containing(
        """
        \t[plugins.nullaway]
        \t\tid = "net.ltgt.nullaway"

        \t\t[plugins.nullaway.version]
        \t\t\tref = "gradle-nullaway-plugin"
        """
      )
      .and()
      .hasFile("build.gradle.kts")
      .notContaining("implementation(libs.error")
      .containing("import net.ltgt.gradle.errorprone.errorprone")
      .containing("import net.ltgt.gradle.nullaway.nullaway")
      .containing(
        """
          alias(libs.plugins.errorprone)
          alias(libs.plugins.nullaway)
        """
      )
      .containing(
        """
        dependencies {
          errorprone("com.uber.nullaway:nullaway:${libs.versions.nullaway.get()}")
        }

        nullaway {
          error()
          requireExplicitNullMarking { error() }
          onlyNullMarked = true
          jspecifyMode = true
        }
        """
      )
      .containing(
        """
        dependencies {
          errorprone("com.google.errorprone:error_prone_core:${libs.versions.error.prone.get()}")
        }

        tasks.compileJava {
          options.errorprone {
            disableAllChecks = true
            excludedPaths = ".*/build/generated/.*"
            nullaway {
              checkContracts = true
            }
          }
        }

        tasks.compileTestJava {
          options.errorprone.enabled = false
        }
        """
      );
  }
}
