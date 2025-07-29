package com.seed4j.generator.server.javatool.modernizer.domain;

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
class ModernizerModuleFactoryTest {

  private final ModernizerModuleFactory factory = new ModernizerModuleFactory();

  @Test
  void shouldBuildModuleForMaven() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

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
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

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
          // jhipster-needle-gradle-plugins
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
