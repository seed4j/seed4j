package com.seed4j.generator.server.springboot.core.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.ModuleFile;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatTwoModulesWithFiles;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.file;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.gradleBuildFile;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.gradleLibsVersionFile;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.generator.buildtool.maven.domain.MavenModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringBootCoreModuleFactoryTest {

  private static final SpringBootCoreModuleFactory factory = new SpringBootCoreModuleFactory();

  @Nested
  class Maven {

    private static final MavenModuleFactory mavenFactory = new MavenModuleFactory();

    @Test
    void shouldBuildModuleOnProjectWithoutDefaultGoal() {
      SeedModuleProperties properties = properties();

      SeedModule mavenModule = mavenFactory.buildMavenModule(properties);
      SeedModule module = factory.buildModule(properties);

      assertThatTwoModulesWithFiles(mavenModule, module, pomFile())
        .hasFile("pom.xml")
        // JUnit BOM should be declared before Spring Boot BOM
        .containingInSequence(
          """
                <dependency>
                  <groupId>org.junit</groupId>
                  <artifactId>junit-bom</artifactId>
                  <version>${junit-jupiter.version}</version>
                  <type>pom</type>
                  <scope>import</scope>
                </dependency>
          """,
          """
                <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-dependencies</artifactId>
                  <version>${spring-boot.version}</version>
                  <type>pom</type>
                  <scope>import</scope>
                </dependency>
          """
        )
        .containing(
          """
              <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter</artifactId>
              </dependency>
          """
        )
        .containing(
          """
              <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-configuration-processor</artifactId>
                <optional>true</optional>
              </dependency>
          """
        )
        .containing(
          """
              <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-lang3</artifactId>
              </dependency>
          """
        )
        .containing(
          """
              <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
                <scope>test</scope>
              </dependency>
          """
        )
        .containing(
          """
                  <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <version>${spring-boot.version}</version>
                    <executions>
                      <execution>
                        <goals>
                          <goal>repackage</goal>
                        </goals>
                      </execution>
                    </executions>
                    <configuration>
                      <mainClass>com.seed4j.growth.MyappApp</mainClass>
                    </configuration>
                  </plugin>
          """
        )
        .containing(
          """
                <plugin>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-maven-plugin</artifactId>
                </plugin>
          """
        )
        .notContaining(
          """
              <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-engine</artifactId>
          """
        )
        .notContaining(
          """
              <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-params</artifactId>
          """
        )
        .notContaining(
          """
              <dependency>
                <groupId>org.assertj</groupId>
                <artifactId>assertj-core</artifactId>
          """
        )
        .notContaining(
          """
              <dependency>
                <groupId>org.mockito</groupId>
                <artifactId>mockito-junit-jupiter</artifactId>
          """
        )
        .containing("    <defaultGoal>spring-boot:run</defaultGoal>")
        .and()
        .hasFile("src/main/java/com/seed4j/growth/MyappApp.java")
        .containing("class MyappApp")
        .and()
        .hasFiles("src/main/java/com/seed4j/growth/ApplicationStartupTraces.java")
        .hasPrefixedFiles("src/test/java/com/seed4j/growth", "ApplicationStartupTracesTest.java", "IntegrationTest.java")
        .hasFile("src/main/resources/config/application.yml")
        .containing(
          """
          logging:
            level:
              com:
                seed4j:
                  growth: INFO
          spring:
            application:
              name: Myapp
          """
        )
        .and()
        .hasFile("src/main/resources/config/application-local.yml")
        .containing(
          """
          logging:
            level:
              com:
                seed4j:
                  growth: DEBUG
          """
        )
        .and()
        .hasFile("src/test/resources/config/application-test.yml")
        .containing(
          """
          logging:
            config: classpath:logback.xml
          spring:
            main:
              banner-mode: 'off'
          """
        )
        .and()
        .hasFiles("src/test/resources/logback.xml", "src/main/resources/logback-spring.xml");
    }

    @Test
    void shouldBuildModuleOnProjectWithDefaultGoal() {
      SeedModuleProperties properties = properties();

      SeedModule module = factory.buildModule(properties);

      assertThatModuleWithFiles(module, pomWithDefaultGoal())
        .hasFile("pom.xml")
        .containing("<defaultGoal>dummy</defaultGoal>")
        .notContaining("<defaultGoal>spring-boot:run</defaultGoal>");
    }

    private ModuleFile pomWithDefaultGoal() {
      return file("src/test/resources/projects/maven-with-default-goal/pom.xml", "pom.xml");
    }
  }

  @Nested
  class Gradle {

    @Test
    void shouldBuildModule() {
      SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
        .basePackage("com.seed4j.growth")
        .projectBaseName("myapp")
        .put("serverPort", 9000)
        .build();

      SeedModule module = factory.buildModule(properties);

      assertThatModuleWithFiles(module, gradleBuildFile(), gradleLibsVersionFile())
        .hasFile("gradle/libs.versions.toml")
        .containing("spring-boot = \"")
        .containing(
          """
          \t[plugins.spring-boot]
          \t\tid = "org.springframework.boot"

          \t\t[plugins.spring-boot.version]
          \t\t\tref = "spring-boot"
          """
        )
        .and()
        .hasFile("build.gradle.kts")
        .containing(
          """
            alias(libs.plugins.spring.boot)
            // seed4j-needle-gradle-plugins
          """
        )
        .containing("defaultTasks(\"bootRun\")")
        .containing(
          """
          springBoot {
            mainClass = "com.seed4j.growth.MyappApp"
          }
          """
        )
        .containing("testImplementation(libs.spring.boot.starter.test)")
        .notContaining("testImplementation(libs.junit.engine)")
        .notContaining("testImplementation(libs.junit.params)")
        .notContaining("testImplementation(libs.assertj)")
        .notContaining("testImplementation(libs.mockito)");
    }
  }

  private SeedModuleProperties properties() {
    return SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();
  }
}
