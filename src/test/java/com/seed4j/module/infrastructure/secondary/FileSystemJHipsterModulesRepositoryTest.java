package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.JHipsterModulesFixture.*;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import ch.qos.logback.classic.Level;
import com.seed4j.Logs;
import com.seed4j.LogsSpy;
import com.seed4j.LogsSpyExtension;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@UnitTest
@ExtendWith(LogsSpyExtension.class)
class FileSystemJHipsterModulesRepositoryTest {

  @Logs
  private LogsSpy logs;

  @Test
  void shouldApplyTwoModulesToMavenProject() {
    JHipsterModule module = module();

    // @formatter:off
    assertThatTwoModulesWithFiles(
      module,
      moduleSecond(module.properties()),
      file("src/test/resources/projects/maven/pom.xml", "pom.xml"),
      packageJsonFile(),
      file("src/test/resources/projects/files/dummy.txt", "dummy.txt")
    )
      .hasFile("pom.xml")
      .containing("<reflections.version>")
      .containing("</reflections.version>")
      .containing(
        """
              <dependency>
                <groupId>org.reflections</groupId>
                <artifactId>reflections</artifactId>
                <version>${reflections.version}</version>
              </dependency>
          """
      )
      .containing("<commons-lang3.version>")
      .containing("</commons-lang3.version>")
      .containing(
        """
                <dependency>
                  <groupId>org.apache.commons</groupId>
                  <artifactId>commons-lang3</artifactId>
                  <version>${commons-lang3.version}</version>
                  <scope>import</scope>
                </dependency>
          """
      );
    // @formatter:on
  }

  @Test
  void shouldApplyModuleToMavenProject() {
    JHipsterModule module = module();

    // @formatter:off
    assertThatModuleWithFiles(
      module,
      file("src/test/resources/projects/maven/pom.xml", "pom.xml"),
      packageJsonFile(),
      file("src/test/resources/projects/files/dummy.txt", "dummy.txt")
    )
      .hasFiles(
        "src/main/java/com/company/myapp/MyApp.java",
        "src/main/java/com/company/myapp/errors/Assert.java",
        "src/main/java/com/company/myapp/errors/AssertionException.java",
        "documentation/cucumber-integration.md",
        ".gitignore"
      )
      .hasExecutableFiles(".husky/pre-commit")
      .hasFile("src/main/java/com/company/myapp/MyApp.java")
        .containing("com.test.myapp")
        .and()
      .hasFile("pom.xml")
        .matchingSavedSnapshot()
        .and()
      .hasFile("package.json")
        .matchingSavedSnapshot()
        .and()
      .hasFile("src/main/java/com/company/myapp/errors/Assert.java")
        .containing("Dummy replacement")
        .containing("Another dummy replacement")
        .containing("Dummy collection replacement")
        .containing("Another dummy collection replacement")
        .containing("// Dummy comment\n  public static final class IntegerAsserter {")
        .notContaining("""
              import java.math.BigDecimal;
              import java.math.BigDecimal;
              """)
        .notContaining("""
              import java.util.Collection;
              import java.util.Collection;
              """)
        .and()
      .hasFile("src/main/resources/config/application.yml")
        .containing("""
          springdoc:
            swagger-ui:
              # This is a comment
              operationsSorter: alpha
          """)
        .and()
      .hasFile("src/main/resources/config/application-local.yml")
        .containing("""
          springdoc:
            swagger-ui:
              # This is a comment
              tryItOutEnabled: 'false'
          """)
        .and()
      .hasFile("src/test/resources/config/application-test.yml")
        .containing("""
          springdoc:
            swagger-ui:
              # This is a comment
              operationsSorter: test
          """)
        .and()
      .hasFile("src/test/resources/config/application-local.yml")
        .containing("""
          springdoc:
            # Swagger properties
            swagger-ui:
              operationsSorter: test
              tagsSorter: test
              # This is a comment
              tryItOutEnabled: test
          """)
        .and()
      .hasFile("src/test/resources/META-INF/spring.factories")
        .containing("o.s.c.ApplicationListener=c.m.m.MyListener1,c.m.m.MyListener2")
        .and()
      .hasFile("README.md")
        .containing(
          """
             - [Cucumber integration](documentation/cucumber-integration.md)
             - [Another cucumber integration](documentation/another-cucumber-integration.md)

             <!-- jhipster-needle-documentation -->
             """
        )
        .containing("docker compose -f src/main/docker/sonar.yml up -d")
        .containing("./mvnw clean verify sonar:sonar")
        .notContaining("./gradlew clean build sonarqube --info")
        .and()
      .hasPrefixedFiles(".git", "config", "HEAD")
      .doNotHaveFiles("dummy.txt")
      .hasFiles("dummy.json");
    // @formatter:on

    assertPreActions();
    assertPostActions();
  }

  @Test
  void shouldApplyModuleToGradleProject() {
    JHipsterModule module = module();

    // @formatter:off
    assertThatModuleWithFiles(
      module,
      gradleBuildFile(),
      gradleLibsVersionFile(),
      packageJsonFile(),
      file("src/test/resources/projects/files/dummy.txt", "dummy.txt")
    )
      .hasFiles(
        "src/main/java/com/company/myapp/MyApp.java",
        "src/main/java/com/company/myapp/errors/Assert.java",
        "src/main/java/com/company/myapp/errors/AssertionException.java",
        "documentation/cucumber-integration.md"
      )
      .hasFile(".gitignore")
        .containing("""
          # Comment
          .my-hidden-folder/*\
          """)
        .and()
      .hasExecutableFiles(".husky/pre-commit")
      .hasFile("src/main/java/com/company/myapp/MyApp.java")
        .containing("com.test.myapp")
        .and()
      .hasFile("gradle/libs.versions.toml")
        .matchingSavedSnapshot()
        .and()
      .hasFile("build.gradle.kts")
        .matchingSavedSnapshot()
        .and()
      .hasFile("buildSrc/build.gradle.kts")
        .containing(
          """
            implementation(libs.gradle.git.properties)
            // jhipster-needle-gradle-implementation-dependencies\
          """
        )
        .and()
      .hasFile("buildSrc/src/main/kotlin/profile-local.gradle.kts")
        .containing(
          """
          import java.util.Properties
          // jhipster-needle-gradle-imports
          """
        )
        .containing(
          """
          plugins {
            java
            checkstyle
            id("com.gorylenko.gradle-git-properties")
            // jhipster-needle-gradle-plugins
          }
          """
        )
        .containing(
          """
          checkstyle {
            toolVersion = libs.versions.checkstyle.get()
          }
          """
        )
        .containing(
          """

          gitProperties {
            failOnNoGitDirectory = false
            keys = listOf("git.branch", "git.commit.id.abbrev", "git.commit.id.describe", "git.build.version")
          }

          // jhipster-needle-gradle-plugins-configurations
          """
        )
        .containing(
          """
            testImplementation(libs.findLibrary("cassandra.unit").get())
            // jhipster-needle-gradle-test-dependencies
          """
        )
        .containing(
          """
          val springProfilesActive by extra("local")
          // jhipster-needle-gradle-properties
          """
        )
        .and()
      .hasFile("package.json")
        .matchingSavedSnapshot()
        .and()
      .hasFile("src/main/java/com/company/myapp/errors/Assert.java")
        .containing("Dummy replacement")
        .containing("Another dummy replacement")
        .containing("Dummy collection replacement")
        .containing("Another dummy collection replacement")
        .containing("// Dummy comment\n  public static final class IntegerAsserter {")
        .notContaining("""
              import java.math.BigDecimal;
              import java.math.BigDecimal;
              """)
        .notContaining("""
              import java.util.Collection;
              import java.util.Collection;
              """)
        .and()
      .hasFile("src/main/resources/config/application.yml")
        .containing("""
          springdoc:
            swagger-ui:
              # This is a comment
              operationsSorter: alpha
          """)
        .and()
      .hasFile("src/main/resources/config/application-local.yml")
        .containing("""
          springdoc:
            swagger-ui:
              # This is a comment
              tryItOutEnabled: 'false'
          """)
        .and()
      .hasFile("src/test/resources/config/application-test.yml")
        .containing("""
          springdoc:
            swagger-ui:
              # This is a comment
              operationsSorter: test
          """)
        .and()
      .hasFile("src/test/resources/config/application-local.yml")
        .containing("""
          springdoc:
            # Swagger properties
            swagger-ui:
              operationsSorter: test
              tagsSorter: test
              # This is a comment
              tryItOutEnabled: test
          """)
        .and()
      .hasFile("src/test/resources/META-INF/spring.factories")
        .containing("o.s.c.ApplicationListener=c.m.m.MyListener1,c.m.m.MyListener2")
        .and()
      .hasFile("README.md")
        .containing(
          """
             - [Cucumber integration](documentation/cucumber-integration.md)
             - [Another cucumber integration](documentation/another-cucumber-integration.md)

             <!-- jhipster-needle-documentation -->
             """
        )
        .containing("docker compose -f src/main/docker/sonar.yml up -d")
        .containing("./gradlew clean build sonarqube --info")
        .notContaining("./mvnw clean verify sonar:sonar")
        .and()
      .hasPrefixedFiles(".git", "config", "HEAD")
      .doNotHaveFiles("dummy.txt")
      .hasFiles("dummy.json");
    // @formatter:on

    assertPreActions();
    assertPostActions();
  }

  private void assertPreActions() {
    logs.shouldHave(Level.DEBUG, "Applying fixture module");
    logs.shouldHave(Level.DEBUG, "You shouldn't add this by default in your modules :D");
  }

  private void assertPostActions() {
    logs.shouldHave(Level.DEBUG, "Fixture module applied");
    logs.shouldHave(Level.DEBUG, "Applied on");
    logs.shouldHave(Level.DEBUG, System.getProperty("java.io.tmpdir"));
  }

  @Test
  void shouldApplyUpgrade() {
    assertThatModuleUpgrade(
      module(),
      upgrade(),
      file("src/test/resources/projects/maven/pom.xml", "pom.xml"),
      packageJsonFile(),
      file("src/test/resources/projects/files/dummy.txt", "dummy.txt")
    )
      .doNotUpdate(".husky/pre-commit")
      .update("src/main/java/com/company/myapp/MyApp.java")
      .doNotHaveFiles("documentation/cucumber-integration.md")
      .hasFile("src/main/java/com/company/myapp/errors/Assert.java")
      .containing("// This is an updated file");
  }
}
