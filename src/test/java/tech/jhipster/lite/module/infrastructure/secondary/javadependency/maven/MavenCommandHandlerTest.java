package tech.jhipster.lite.module.infrastructure.secondary.javadependency.maven;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.lite.module.domain.JHipsterModule.javaBuildPlugin;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.defaultVersionDependency;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.jsonWebTokenDependencyId;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.mavenEnforcerPlugin;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.mavenEnforcerPluginManagement;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.optionalTestDependency;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.springBootDependencyId;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.springBootDependencyManagement;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.springBootStarterWebDependency;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.springBootVersion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.javabuild.command.AddBuildPluginManagement;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaBuildPlugin;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyVersion;

@UnitTest
class MavenCommandHandlerTest {

  @Test
  void shouldNotCreateHandlerFromRandomFile() {
    assertThatThrownBy(() -> new MavenCommandHandler(Indentation.DEFAULT, Paths.get("src/test/resources/projects/empty/.gitkeep")))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAppendEncodingHeader() {
    Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

    new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetVersion(springBootVersion()));

    assertThat(content(pom)).startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
  }

  @Nested
  @DisplayName("Set dependency version")
  class MavenCommandHandlerSetVersionTest {

    @Test
    void shouldNotAddPropertiesToPomWithOnlyRootDefined() {
      Path pom = projectWithPom("src/test/resources/projects/root-only-maven/pom.xml");

      assertThatThrownBy(() -> new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetVersion(springBootVersion())))
        .isExactlyInstanceOf(InvalidPomException.class);
    }

    @Test
    void shouldAddPropertiesToPomWithoutProperties() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetVersion(springBootVersion()));

      assertThat(content(pom))
        .contains("  <properties>")
        .contains("    <spring-boot.version>1.2.3</spring-boot.version>")
        .contains("  </properties>");
    }

    @Test
    void shouldAddPropertiesToPomWithProperties() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetVersion(springBootVersion()));

      assertThat(content(pom)).contains("    <spring-boot.version>1.2.3</spring-boot.version>").doesNotContain(">  ");
    }

    @Test
    void shouldUpdateExistingProperty() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetVersion(new JavaDependencyVersion("jjwt", "0.12.0")));

      assertThat(content(pom))
        .contains("    <jjwt.version>0.12.0</jjwt.version>")
        .doesNotContain("    <jjwt.version>0.11.5</jjwt.version>")
        .doesNotContain(">  ");
    }
  }

  @Nested
  @DisplayName("Remove dependency management")
  class MavenCommandHandlerRemoveDependencyManagementTest {

    @Test
    void shouldNotRemoveUnknownDependency() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      assertThatCode(() ->
          new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new RemoveJavaDependencyManagement(jsonWebTokenDependencyId()))
        )
        .doesNotThrowAnyException();
    }

    @Test
    void shouldRemoveDependency() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new RemoveJavaDependencyManagement(springBootDependencyId()));

      assertThat(content(pom))
        .doesNotContain(
          """
                <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-dependencies</artifactId>
                  <version>${spring-boot.version}</version>
                  <scope>import</scope>
                  <type>pom</type>
                </dependency>
              </dependencies>
            </dependencyManagement>
          """
        );
    }
  }

  @Nested
  @DisplayName("Add dependency management")
  class MavenCommandHandlerAddDependencyManagementTest {

    @Test
    void shouldAddDepencyInPomWithoutDependenciesManagement() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaDependencyManagement(springBootDependencyManagement()));

      assertThat(content(pom))
        .contains(
          """
            <dependencyManagement>
              <dependencies>
                <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-dependencies</artifactId>
                  <version>${spring-boot.version}</version>
                  <scope>import</scope>
                  <type>pom</type>
                </dependency>
              </dependencies>
            </dependencyManagement>
          """
        );
    }

    @Test
    void shouldAddDependencyInPomWithDependenciesManagement() {
      Path pom = projectWithPom("src/test/resources/projects/maven-empty-dependencies/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaDependencyManagement(springBootDependencyManagement()));

      assertThat(content(pom))
        .contains(
          """
                <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-dependencies</artifactId>
                  <version>${spring-boot.version}</version>
                  <scope>import</scope>
                  <type>pom</type>
                </dependency>
              </dependencies>
            </dependencyManagement>
          """
        );
    }

    @Test
    void shouldAddDependencyManagementWithExclusion() {
      Path pom = projectWithPom("src/test/resources/projects/maven-empty-dependencies/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaDependencyManagement(springBootStarterWebDependency()));

      assertThat(content(pom))
        .contains(
          """
                <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-web</artifactId>
                  <exclusions>
                    <exclusion>
                      <groupId>org.springframework.boot</groupId>
                      <artifactId>spring-boot-starter-tomcat</artifactId>
                    </exclusion>
                  </exclusions>
                </dependency>
              </dependencies>
            </dependencyManagement>
          """
        );
    }
  }

  @Nested
  @DisplayName("Remove dependency")
  class MavenCommandHandlerRemoveDependencyTest {

    @Test
    void shouldNotRemoveUnknownDependency() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      assertThatCode(() ->
          new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new RemoveDirectJavaDependency(jsonWebTokenDependencyId()))
        )
        .doesNotThrowAnyException();
    }

    @Test
    void shouldRemoveDependency() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new RemoveDirectJavaDependency(jsonWebTokenDependencyId()));

      assertThat(content(pom))
        .doesNotContain("      <groupId>io.jsonwebtoken</groupId>")
        .doesNotContain("      <artifactId>jjwt-api</artifactId>")
        .doesNotContain("      <version>${jjwt.version}</version>")
        .doesNotContain("      <scope>test</scope>")
        .doesNotContain("      <optional>true</optional>");
    }

    @Test
    void shouldRemoveDependencyWithFullyMatchingId() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-multiple-webtoken/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new RemoveDirectJavaDependency(jsonWebTokenDependencyId()));

      assertThat(content(pom))
        .contains(
          """
              <dependency>
                <groupId>io.jsonwebtoken</groupId>
                <artifactId>jjwt-implementation</artifactId>
              </dependency>

              <dependency>
                <groupId>io.another</groupId>
                <artifactId>jjwt-api</artifactId>
              </dependency>
          """
        )
        .doesNotContain("      <version>${jjwt.version}</version>")
        .doesNotContain("      <scope>test</scope>")
        .doesNotContain("      <optional>true</optional>");
    }
  }

  @Nested
  @DisplayName("Add dependency")
  class MavenCommandHandlerAddDependencyTest {

    @Test
    void shouldAddDepencyInPomWithoutDependencies() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddDirectJavaDependency(optionalTestDependency()));

      assertThat(content(pom))
        .contains(
          """
            <dependencies>
              <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-engine</artifactId>
                <version>${spring-boot.version}</version>
                <classifier>test</classifier>
                <scope>test</scope>
                <optional>true</optional>
              </dependency>
            </dependencies>
          """
        );
    }

    @Test
    void shouldAddTestDependencyInPomWithDependencies() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddDirectJavaDependency(optionalTestDependency()));

      String content = content(pom);
      assertThat(content)
        .contains(
          """
              <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-engine</artifactId>
                <version>${spring-boot.version}</version>
                <classifier>test</classifier>
                <scope>test</scope>
                <optional>true</optional>
              </dependency>
            </dependencies>
          """
        );

      assertThat(Pattern.compile("^ +$", Pattern.MULTILINE).matcher(content).find()).isFalse();
    }

    @Test
    void shouldAddDependencyWithExclusion() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddDirectJavaDependency(springBootStarterWebDependency()));

      String content = content(pom);
      assertThat(content)
        .contains(
          """
               <dependency>
                 <groupId>org.springframework.boot</groupId>
                 <artifactId>spring-boot-starter-web</artifactId>
                 <exclusions>
                   <exclusion>
                     <groupId>org.springframework.boot</groupId>
                     <artifactId>spring-boot-starter-tomcat</artifactId>
                   </exclusion>
                 </exclusions>
               </dependency>
           """
        );
    }

    @Test
    void shouldAddCompileDependencyInPomWithDependencies() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddDirectJavaDependency(defaultVersionDependency()));

      assertThat(content(pom))
        .contains(
          """
                <artifactId>logstash-logback-encoder</artifactId>
              </dependency>

              <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter</artifactId>
              </dependency>

              <dependency>
                <groupId>io.jsonwebtoken</groupId>
          """
        );
    }

    @Test
    void shouldAddCompileDependencyInPomWithOnlyCompileDependencies() {
      Path pom = projectWithPom("src/test/resources/projects/maven-compile-only/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddDirectJavaDependency(defaultVersionDependency()));

      assertThat(content(pom))
        .contains(
          """
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter</artifactId>
              </dependency>
            </dependencies>
          """
        );
    }

    @Test
    void shouldAddCompileDependencyInPomWithEmptyDependencies() {
      Path pom = projectWithPom("src/test/resources/projects/maven-empty-dependencies/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddDirectJavaDependency(defaultVersionDependency()));

      assertThat(content(pom))
        .contains(
          """
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter</artifactId>
              </dependency>
            </dependencies>
          """
        );
    }
  }

  @Nested
  @DisplayName("Add build plugin management")
  class MavenCommandHandlerAddBuildPluginManagementTest {

    @Test
    void shouldHandleMalformedAdditionalElements() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      assertThatThrownBy(() ->
          new MavenCommandHandler(Indentation.DEFAULT, pom)
            .handle(
              new AddBuildPluginManagement(
                javaBuildPlugin()
                  .groupId("org.apache.maven.plugins")
                  .artifactId("maven-enforcer-plugin")
                  .additionalElements("<dummy")
                  .build()
              )
            )
        )
        .isExactlyInstanceOf(MalformedAdditionalInformationException.class);
    }

    @Test
    void shouldAddBuildPluginManagementToEmptyPom() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      addMavenEnforcerPlugin(pom);

      assertThat(content(pom)).contains(pluginManagement());
    }

    @Test
    void shouldAddBuildPluginManagementToPomWithoutPluginManagement() {
      Path pom = projectWithPom("src/test/resources/projects/maven-empty-build/pom.xml");

      addMavenEnforcerPlugin(pom);

      assertThat(content(pom))
        .contains(pluginManagement())
        .doesNotContain("""
                <build>
                </build>
              """);
    }

    @Test
    void shouldAddBuildPluginManagementToPomWithEmptyPluginManagement() {
      Path pom = projectWithPom("src/test/resources/projects/maven-empty-plugin-managment/pom.xml");

      addMavenEnforcerPlugin(pom);

      assertThat(content(pom))
        .contains(pluginManagement())
        .doesNotContain(
          """
                <build>
                  <pluginManagement>
                  </pluginManagement>
                </build>
              """
        );
    }

    @Test
    void shouldAddBuildPluginManagementToPomWithPluginManagementPlugins() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      addMavenEnforcerPlugin(pom);

      assertThat(content(pom)).contains(pluginManagement());
    }

    private void addMavenEnforcerPlugin(Path pom) {
      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddBuildPluginManagement(mavenEnforcerPluginManagement()));
    }

    private String pluginManagement() {
      return """
                  <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-enforcer-plugin</artifactId>
                    <version>${maven-enforcer-plugin.version}</version>
                    <executions>
                      <execution>
                        <id>enforce-versions</id>
                        <goals>
                          <goal>enforce</goal>
                        </goals>
                      </execution>
                      <execution>
                        <id>enforce-dependencyConvergence</id>
                        <configuration>
                          <rules>
                            <DependencyConvergence/>
                          </rules>
                          <fail>false</fail>
                        </configuration>
                        <goals>
                          <goal>enforce</goal>
                        </goals>
                      </execution>
                    </executions>
                    <configuration>
                      <rules>
                        <requireMavenVersion>
                          <message>You are running an older version of Maven. JHipster requires at least Maven ${maven.version}</message>
                          <version>[${maven.version},)</version>
                        </requireMavenVersion>
                        <requireJavaVersion>
                          <message>You are running an incompatible version of Java. JHipster supports JDK 17.</message>
                          <version>[17,18)</version>
                        </requireJavaVersion>
                      </rules>
                    </configuration>
                  </plugin>
                </plugins>
              </pluginManagement>
            </build>
          """;
    }
  }

  @Nested
  @DisplayName("Add build plugin")
  class MavenCommandHandlerAddBuildPluginTest {

    @Test
    void shouldAddBuildPluginToEmptyPom() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      addMavenEnforcerPlugin(pom);

      assertThat(content(pom)).contains(plugins());
    }

    @Test
    void shouldAddBuildPluginToPomWithEmptyBuild() {
      Path pom = projectWithPom("src/test/resources/projects/maven-empty-build/pom.xml");

      addMavenEnforcerPlugin(pom);

      assertThat(content(pom)).contains(plugins()).doesNotContain("""
                <build>
                </build>
              """);
    }

    @Test
    void shouldAddBuildPluginToPomWithPlugins() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      addMavenEnforcerPlugin(pom);

      assertThat(content(pom))
        .contains(plugins())
        .doesNotContain(
          """
                  <plugins>
                    <plugin>
                      <groupId>org.springframework.boot</groupId>
                      <artifactId>spring-boot-maven-plugin</artifactId>
                    </plugin>
                  </plugins>
              """
        );
    }

    private void addMavenEnforcerPlugin(Path pom) {
      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddDirectJavaBuildPlugin(mavenEnforcerPlugin()));
    }

    private String plugins() {
      return """
                <plugin>
                  <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-enforcer-plugin</artifactId>
                </plugin>
              </plugins>
          """;
    }
  }

  private static Path projectWithPom(String sourcePom) {
    Path folder = Paths.get(TestFileUtils.tmpDirForTest());

    try {
      Files.createDirectories(folder);
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    Path pomPath = folder.resolve("pom.xml");
    try {
      Files.copy(Paths.get(sourcePom), pomPath);
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    return pomPath;
  }

  private static String content(Path path) {
    try {
      return Files.readString(path);
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }
}
