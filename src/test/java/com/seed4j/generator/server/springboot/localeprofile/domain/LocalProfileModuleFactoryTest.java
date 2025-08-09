package com.seed4j.generator.server.springboot.localeprofile.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class LocalProfileModuleFactoryTest {

  private static final LocalProfileModuleFactory factory = new LocalProfileModuleFactory();

  @Nested
  class Maven {

    @Test
    void shouldBuildModule() {
      SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

      JHipsterModule module = factory.buildModule(properties);

      assertThatModuleWithFiles(module, pomFile())
        .hasFile("pom.xml")
        .containing(
          """
            <profiles>
              <profile>
                <id>local</id>
                <activation>
                  <activeByDefault>true</activeByDefault>
                </activation>
                <properties>
                  <spring.profiles.active>local</spring.profiles.active>
                </properties>
              </profile>
            </profiles>
          """
        )
        .containing(
          """
                  <plugin>
                    <artifactId>maven-resources-plugin</artifactId>
                    <version>${maven-resources-plugin.version}</version>
                    <configuration>
                      <useDefaultDelimiters>false</useDefaultDelimiters>
                      <delimiters>
                        <delimiter>@</delimiter>
                      </delimiters>
                    </configuration>
                  </plugin>
          """
        )
        .and()
        .hasFile("src/main/resources/config/application.yml")
        .containing(
          """
          spring:
            profiles:
              active: '@spring.profiles.active@'
          """
        );
    }

    @Test
    void shouldReplaceCIActions() {
      SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

      JHipsterModule module = factory.buildModule(properties);

      assertThatModuleWithFiles(module, pomFile(), githubActionsBuild(), gitlabCI())
        .hasFile("pom.xml")
        .and()
        .hasFile(".github/workflows/github-actions.yml")
        .containing(
          """
          mvnw clean verify -P'!local' $MAVEN_CLI_OPTS
          """
        )
        .and()
        .hasFile(".gitlab-ci.yml")
        .containing(
          """
          mvnw clean verify -P'!local' $MAVEN_CLI_OPTS
          """
        );
    }

    private static ModuleFile githubActionsBuild() {
      return file("src/test/resources/projects/ci/github-actions-maven.yml", ".github/workflows/github-actions.yml");
    }

    private static ModuleFile gitlabCI() {
      return file("src/test/resources/projects/ci/.gitlab-ci-maven.yml", ".gitlab-ci.yml");
    }
  }

  @Nested
  class Gradle {

    @Test
    void shouldBuildModule() {
      SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

      JHipsterModule module = factory.buildModule(properties);

      assertThatModuleWithFiles(module, gradleBuildFile(), gradleLibsVersionFile())
        .hasFile("build.gradle.kts")
        .containing(
          """
          val springProfilesActive by extra("")
          // seed4j-needle-gradle-properties
          """
        )
        .containing(
          """
          val profiles = (project.findProperty("profiles") as String? ?: "")
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
          if (profiles.isEmpty() || profiles.contains("local")) {
            apply(plugin = "profile-local")
          }
          // seed4j-needle-profile-activation\
          """
        )
        .containing(
          """
          tasks.build {
            dependsOn("processResources")
          }

          tasks.processResources {
            filesMatching("**/*.yml") {
              filter { it.replace("@spring.profiles.active@", springProfilesActive) }
            }
            filesMatching("**/*.properties") {
              filter { it.replace("@spring.profiles.active@", springProfilesActive) }
            }
          }

          // seed4j-needle-gradle-free-configuration-blocks\
          """
        )
        .and()
        .hasFile("buildSrc/src/main/kotlin/profile-local.gradle.kts")
        .containing(
          """
          val springProfilesActive by extra("local")
          // seed4j-needle-gradle-properties
          """
        )
        .and()
        .hasFile("src/main/resources/config/application.yml")
        .containing(
          """
          spring:
            profiles:
              active: '@spring.profiles.active@'
          """
        );
    }

    @Test
    void shouldReplaceCIActions() {
      SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

      JHipsterModule module = factory.buildModule(properties);

      assertThatModuleWithFiles(module, pomFile(), githubActionsBuild(), gitlabCI())
        .hasFile("pom.xml")
        .and()
        .hasFile(".github/workflows/github-actions.yml")
        .containing(
          """
          ./gradlew clean integrationTest -Pprofile=local --no-daemon
          """
        )
        .and()
        .hasFile(".gitlab-ci.yml")
        .containing(
          """
          ./gradlew clean integrationTest -Pprofile=local $GRADLE_CLI_OPTS
          """
        );
    }

    private static ModuleFile githubActionsBuild() {
      return file("src/test/resources/projects/ci/github-actions-gradle.yml", ".github/workflows/github-actions.yml");
    }

    private static ModuleFile gitlabCI() {
      return file("src/test/resources/projects/ci/.gitlab-ci-gradle.yml", ".gitlab-ci.yml");
    }
  }
}
