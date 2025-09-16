package com.seed4j.generator.server.springboot.technicaltools.gitinfo.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class GitInfoModuleFactoryTest {

  private static final GitInfoModuleFactory factory = new GitInfoModuleFactory();

  @Nested
  class Maven {

    @Test
    void shouldAddGitInformation() {
      Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
        .basePackage("com.seed4j.growth")
        .projectBaseName("myapp")
        .build();

      Seed4JModule module = factory.buildModule(properties);

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
      Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
        .basePackage("com.seed4j.growth")
        .projectBaseName("myapp")
        .build();

      Seed4JModule module = factory.buildModule(properties);

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
