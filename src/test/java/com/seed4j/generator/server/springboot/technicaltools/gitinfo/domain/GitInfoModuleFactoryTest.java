package com.seed4j.generator.server.springboot.technicaltools.gitinfo.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class GitInfoModuleFactoryTest {

  private static final GitInfoModuleFactory factory = new GitInfoModuleFactory();

  @Nested
  class Maven {

    @Test
    void shouldAddGitInformation() {
      JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
        .basePackage("com.seed4j.growth")
        .projectBaseName("myapp")
        .build();

      JHipsterModule module = factory.buildModule(properties);

      // @formatter:off
      assertThatModuleWithFiles(module, pomFile())
        .hasFile("pom.xml")
          .containing(
            """
                  <plugin>
                    <groupId>io.github.git-commit-id</groupId>
                    <artifactId>git-commit-id-maven-plugin</artifactId>
                  </plugin>
            """
          )
          .and()
        .hasFile("src/main/resources/config/application.yml")
          .containing(
            """
            management:
              info:
                env:
                  enabled: true
                # Git Information
                git:
                  enabled: true
                  mode: full
            """
          )
          .and()
        .hasPrefixedFiles("src/main/java/com/seed4j/growth/wire/gitinfo", "infrastructure/primary/GitInfoConfiguration.java", "package-info.java");
      // @formatter:on
    }
  }

  @Nested
  class Gradle {

    @Test
    void shouldAddGitInformation() {
      JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
        .basePackage("com.seed4j.growth")
        .projectBaseName("myapp")
        .build();

      JHipsterModule module = factory.buildModule(properties);

      assertThatModuleWithFiles(module, gradleBuildFile(), gradleLibsVersionFile())
        .hasFile("gradle/libs.versions.toml")
        .containing(
          """
          id = "com.gorylenko.gradle-git-properties"
          """
        )
        .and()
        .hasFile("build.gradle.kts")
        .containing(
          """
          alias(libs.plugins.git.properties)
          """
        );
    }
  }
}
