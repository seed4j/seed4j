package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import static com.seed4j.TestFileUtils.contentNormalizingNewLines;
import static com.seed4j.TestFileUtils.tmpDirForTest;
import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.dependencyId;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.javaDependency;
import static com.seed4j.module.domain.Seed4JModule.mavenPlugin;
import static com.seed4j.module.domain.Seed4JModulesFixture.asciidoctorPlugin;
import static com.seed4j.module.domain.Seed4JModulesFixture.checkstyleGradlePlugin;
import static com.seed4j.module.domain.Seed4JModulesFixture.defaultVersionDependency;
import static com.seed4j.module.domain.Seed4JModulesFixture.googleAutoServiceAnnotationProcessor;
import static com.seed4j.module.domain.Seed4JModulesFixture.hibernateAnnotationProcessor;
import static com.seed4j.module.domain.Seed4JModulesFixture.jsonWebTokenDependencyId;
import static com.seed4j.module.domain.Seed4JModulesFixture.localBuildProfile;
import static com.seed4j.module.domain.Seed4JModulesFixture.mavenBuildExtensionWithSlug;
import static com.seed4j.module.domain.Seed4JModulesFixture.mavenEnforcerPlugin;
import static com.seed4j.module.domain.Seed4JModulesFixture.mavenEnforcerPluginManagement;
import static com.seed4j.module.domain.Seed4JModulesFixture.mavenEnforcerVersion;
import static com.seed4j.module.domain.Seed4JModulesFixture.optionalTestDependency;
import static com.seed4j.module.domain.Seed4JModulesFixture.springBootDependencyId;
import static com.seed4j.module.domain.Seed4JModulesFixture.springBootDependencyManagement;
import static com.seed4j.module.domain.Seed4JModulesFixture.springBootStarterWebDependency;
import static com.seed4j.module.domain.Seed4JModulesFixture.springBootVersion;
import static com.seed4j.module.domain.Seed4JModulesFixture.springProfilesActiveProperty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.buildproperties.BuildProperty;
import com.seed4j.module.domain.buildproperties.PropertyKey;
import com.seed4j.module.domain.buildproperties.PropertyValue;
import com.seed4j.module.domain.javabuild.command.AddDirectJavaDependency;
import com.seed4j.module.domain.javabuild.command.AddDirectMavenPlugin;
import com.seed4j.module.domain.javabuild.command.AddGradlePlugin;
import com.seed4j.module.domain.javabuild.command.AddJavaAnnotationProcessor;
import com.seed4j.module.domain.javabuild.command.AddJavaBuildProfile;
import com.seed4j.module.domain.javabuild.command.AddJavaDependencyManagement;
import com.seed4j.module.domain.javabuild.command.AddMavenBuildExtension;
import com.seed4j.module.domain.javabuild.command.AddMavenPluginManagement;
import com.seed4j.module.domain.javabuild.command.RemoveDirectJavaDependency;
import com.seed4j.module.domain.javabuild.command.RemoveJavaAnnotationProcessor;
import com.seed4j.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import com.seed4j.module.domain.javabuild.command.SetBuildProperty;
import com.seed4j.module.domain.javabuild.command.SetVersion;
import com.seed4j.module.domain.javabuildprofile.BuildProfileActivation;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.module.domain.javadependency.DependencyId;
import com.seed4j.module.domain.javadependency.JavaDependencyVersion;
import com.seed4j.shared.error.domain.GeneratorException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class MavenCommandHandlerTest {

  @Test
  void shouldNotCreateHandlerFromRandomFile() {
    assertThatThrownBy(() ->
      new MavenCommandHandler(Indentation.DEFAULT, Path.of("src/test/resources/projects/empty/.gitkeep"))
    ).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAppendEncodingHeader() {
    Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

    new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetVersion(springBootVersion()));

    assertThat(contentNormalizingNewLines(pom)).startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
  }

  @Test
  void shouldEnforceIndentation() {
    Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

    new MavenCommandHandler(Indentation.from(4), pom).handle(new SetVersion(springBootVersion()));

    assertThat(contentNormalizingNewLines(pom))
      .contains("    <properties>")
      .contains("        <spring-boot.version>1.2.3</spring-boot.version>")
      .contains("    </properties>");
  }

  @Nested
  class HandleSetVersion {

    @Test
    void shouldAddPropertiesToPomWithOnlyRootDefined() {
      Path pom = projectWithPom("src/test/resources/projects/root-only-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetVersion(springBootVersion()));

      assertThat(contentNormalizingNewLines(pom))
        .contains("  <properties>")
        .contains("    <spring-boot.version>1.2.3</spring-boot.version>")
        .contains("  </properties>");
    }

    @Test
    void shouldAddPropertiesToPomWithoutProperties() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetVersion(springBootVersion()));

      assertThat(contentNormalizingNewLines(pom))
        .contains("  <properties>")
        .contains("    <spring-boot.version>1.2.3</spring-boot.version>")
        .contains("  </properties>");
    }

    @Test
    void shouldAddPropertiesToPomWithProperties() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetVersion(springBootVersion()));

      assertThat(contentNormalizingNewLines(pom)).contains("    <spring-boot.version>1.2.3</spring-boot.version>").doesNotContain(">  ");
    }

    @Test
    void shouldUpdateExistingProperty() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetVersion(new JavaDependencyVersion("json-web-token", "0.12.0")));

      assertThat(contentNormalizingNewLines(pom))
        .contains("    <json-web-token.version>0.12.0</json-web-token.version>")
        .doesNotContain("    <json-web-token.version>0.11.5</json-web-token.version>")
        .doesNotContain(">  ");
    }
  }

  @Nested
  class HandleSetBuildProperty {

    @Nested
    class WithoutProfile {

      @Test
      void shouldAddPropertiesToPomWithOnlyRootDefined() {
        Path pom = projectWithPom("src/test/resources/projects/root-only-maven/pom.xml");

        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetBuildProperty(springProfilesActiveProperty()));

        assertThat(contentNormalizingNewLines(pom)).contains(
          """
            <properties>
              <spring.profiles.active>local</spring.profiles.active>
            </properties>
          """
        );
      }

      @Test
      void shouldAddPropertiesToPomWithoutProperties() {
        Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetBuildProperty(springProfilesActiveProperty()));

        assertThat(contentNormalizingNewLines(pom)).contains(
          """
            <properties>
              <spring.profiles.active>local</spring.profiles.active>
            </properties>
          """
        );
      }

      @Test
      void shouldAddPropertiesToPomWithProperties() {
        Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetBuildProperty(springProfilesActiveProperty()));

        assertThat(contentNormalizingNewLines(pom))
          .contains("    <spring.profiles.active>local</spring.profiles.active>")
          .doesNotContain(">  ");
      }

      @Test
      void shouldUpdateExistingProperty() {
        Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(
          new SetBuildProperty(new BuildProperty(new PropertyKey("spring.profiles.active"), new PropertyValue("dev")))
        );

        assertThat(contentNormalizingNewLines(pom))
          .contains("    <spring.profiles.active>dev</spring.profiles.active>")
          .doesNotContain("    <spring.profiles.active>local</spring.profiles.active>")
          .doesNotContain(">  ");
      }
    }

    @Nested
    class WithProfile {

      @Test
      void shouldNotAddPropertiesToPomWithoutProfile() {
        Path pom = projectWithPom("src/test/resources/projects/root-only-maven/pom.xml");

        assertThatThrownBy(() ->
          new MavenCommandHandler(Indentation.DEFAULT, pom).handle(
            new SetBuildProperty(springProfilesActiveProperty(), localBuildProfile())
          )
        ).isExactlyInstanceOf(MissingMavenProfileException.class);
      }

      @Test
      void shouldAddPropertiesToPomProfileWithoutProperties() {
        Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");

        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetBuildProperty(springProfilesActiveProperty(), localBuildProfile()));

        assertThat(contentNormalizingNewLines(pom)).contains(
          """
            <profiles>
              <profile>
                <id>local</id>
                <properties>
                  <spring.profiles.active>local</spring.profiles.active>
                </properties>
              </profile>
            </profiles>
          """
        );
      }

      @Test
      void shouldAddPropertiesToPomProfileWithProperties() {
        Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile-and-properties/pom.xml");

        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetBuildProperty(springProfilesActiveProperty(), localBuildProfile()));

        assertThat(contentNormalizingNewLines(pom))
          .contains(
            """
                    <spring.profiles.active>local</spring.profiles.active>
                  </properties>
                </profile>
              </profiles>
            """
          )
          .doesNotContain(">  ");
      }

      @Test
      void shouldUpdateExistingProperty() {
        Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile-and-properties/pom.xml");
        MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
        mavenCommandHandler.handle(new SetBuildProperty(springProfilesActiveProperty(), localBuildProfile()));

        mavenCommandHandler.handle(
          new SetBuildProperty(new BuildProperty(new PropertyKey("spring.profiles.active"), new PropertyValue("dev")), localBuildProfile())
        );

        assertThat(contentNormalizingNewLines(pom))
          .contains(
            """
                    <spring.profiles.active>dev</spring.profiles.active>
                  </properties>
                </profile>
              </profiles>
            """
          )
          .doesNotContain("<spring.profiles.active>local</spring.profiles.active>");
      }
    }
  }

  @Nested
  class HandleAddJavaBuildProfile {

    @Test
    void shouldAddProfileToPomWithOnlyRootDefined() {
      Path pom = projectWithPom("src/test/resources/projects/root-only-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaBuildProfile(localBuildProfile()));

      assertThat(contentNormalizingNewLines(pom)).contains(
        """
            <profile>
              <id>local</id>
            </profile>
          </profiles>
        """
      );
    }

    @Test
    void shouldAddProfileToPomWithoutProfiles() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(
        new AddJavaBuildProfile(localBuildProfile(), BuildProfileActivation.builder().activeByDefault().build())
      );

      assertThat(contentNormalizingNewLines(pom)).contains(
        """
          <profiles>
            <profile>
              <id>local</id>
              <activation>
                <activeByDefault>true</activeByDefault>
              </activation>
            </profile>
          </profiles>
        """
      );
    }

    @Test
    void shouldAddProfileToPomWithProfiles() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaBuildProfile(new BuildProfileId("dev")));

      assertThat(contentNormalizingNewLines(pom)).contains(
        """
            <profile>
              <id>dev</id>
            </profile>
          </profiles>
        """
      );
    }

    @Test
    void shouldNotDuplicateExistingProfile() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaBuildProfile(localBuildProfile()));

      assertThat(contentNormalizingNewLines(pom)).contains(
        """
          <profiles>
            <profile>
              <id>local</id>
            </profile>
          </profiles>
        """
      );
    }

    @Test
    void shouldAddProfileWithEmptyActivation() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(
        new AddJavaBuildProfile(localBuildProfile(), BuildProfileActivation.builder().build())
      );

      assertThat(contentNormalizingNewLines(pom)).contains(
        """
          <profiles>
            <profile>
              <id>local</id>
              <activation />
            </profile>
          </profiles>
        """
      );
    }
  }

  @Nested
  class HandleRemoveJavaDependencyManagement {

    @Test
    void shouldNotRemoveUnknownDependency() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      assertThatCode(() ->
        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new RemoveJavaDependencyManagement(jsonWebTokenDependencyId()))
      ).doesNotThrowAnyException();
    }

    @Test
    void shouldRemoveDependency() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");
      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
      mavenCommandHandler.handle(new AddJavaDependencyManagement(springBootDependencyManagement()));

      mavenCommandHandler.handle(new RemoveJavaDependencyManagement(springBootDependencyId()));

      assertThat(contentNormalizingNewLines(pom)).doesNotContain(
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
    void shouldRemoveDependencyAndVersion() {
      Path pom = projectWithPom("src/test/resources/projects/maven-empty-dependencies/pom.xml");
      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
      mavenCommandHandler.handle(new SetVersion(springBootVersion()));
      mavenCommandHandler.handle(new AddJavaDependencyManagement(springBootDependencyManagement()));

      mavenCommandHandler.handle(new RemoveJavaDependencyManagement(springBootDependencyId()));

      assertThat(contentNormalizingNewLines(pom))
        .doesNotContain("<spring-boot.version>")
        .doesNotContain("</spring-boot.version>")
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

    @Test
    void shouldRemoveDependencyButKeepVersionIfStillUsed() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-multiple-dependencies-management/pom.xml");
      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);

      mavenCommandHandler.handle(new RemoveJavaDependencyManagement(dependencyId("org.springframework.boot", "spring-boot-starter-web")));
      mavenCommandHandler.handle(
        new RemoveJavaDependencyManagement(dependencyId("org.junit.jupiter", "junit-jupiter-engine"), localBuildProfile())
      );

      assertThat(contentNormalizingNewLines(pom))
        .doesNotContain(
          """
            <dependencyManagement>
              <dependencies>
                <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-web</artifactId>
                  <version>${spring-boot.version}</version>
                </dependency>
          """
        )
        .doesNotContain(
          """
            <profile>
              <id>local</id>
                <dependencyManagement>
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
                </dependencyManagement>
          """
        )
        .contains("<spring-boot.version>")
        .contains("</spring-boot.version>")
        .contains(
          """
                <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-data-jpa</artifactId>
                  <version>${spring-boot.version}</version>
                </dependency>
              </dependencies>
            </dependencyManagement>
          """
        );
    }

    @Test
    void shouldRemoveDependencyFromProfile() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");
      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
      mavenCommandHandler.handle(new AddJavaDependencyManagement(springBootStarterWebDependency(), localBuildProfile()));

      mavenCommandHandler.handle(
        new RemoveJavaDependencyManagement(
          DependencyId.of(groupId("org.springframework.boot"), artifactId("spring-boot-starter-web")),
          localBuildProfile()
        )
      );

      assertThat(contentNormalizingNewLines(pom))
        .contains(
          """
              <profile>
                <id>local</id>
                <dependencyManagement>
                </dependencyManagement>
              </profile>
          """
        )
        .doesNotContain("<groupId>org.springframework.boot</groupId>")
        .doesNotContain("<artifactId>spring-boot-starter-web</artifactId>");
    }

    @Test
    void shouldRemoveDependencyAndVersionFromProfile() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");
      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
      mavenCommandHandler.handle(new SetVersion(springBootVersion()));
      mavenCommandHandler.handle(new AddJavaDependencyManagement(springBootDependencyManagement(), localBuildProfile()));

      mavenCommandHandler.handle(new RemoveJavaDependencyManagement(springBootDependencyId(), localBuildProfile()));

      assertThat(contentNormalizingNewLines(pom))
        .doesNotContain("<spring-boot.version>")
        .doesNotContain("</spring-boot.version>")
        .contains(
          """
              <profile>
                <id>local</id>
                <dependencyManagement>
                </dependencyManagement>
              </profile>
          """
        )
        .doesNotContain(
          """
                  <dependencies>
                    <dependency>
                      <groupId>org.springframework.boot</groupId>
                      <artifactId>spring-boot-dependencies</artifactId>
                      <version>${spring-boot.version}</version>
                      <type>pom</type>
                      <scope>import</scope>
                    </dependency>
                  </dependencies>
                </dependencyManagement>
              </profile>
            </profiles>
          """
        );
    }

    @Test
    void shouldRemoveDependencyButKeepVersionIfStillUsedByProfile() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-multiple-dependencies-management/pom.xml");
      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);

      mavenCommandHandler.handle(new RemoveJavaDependencyManagement(dependencyId("org.springframework.boot", "spring-boot-starter-web")));
      mavenCommandHandler.handle(
        new RemoveJavaDependencyManagement(dependencyId("org.springframework.boot", "spring-boot-starter-data-jpa"))
      );

      assertThat(contentNormalizingNewLines(pom))
        .contains(
          """
            <dependencyManagement>
            </dependencyManagement>
          """
        )
        .contains("<spring-boot.version>")
        .contains("</spring-boot.version>")
        .contains(
          """
              <profile>
                <id>local</id>
                <dependencyManagement>
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
                </dependencyManagement>
          """
        );
    }
  }

  @Nested
  class HandleAddJavaDependencyManagement {

    @Test
    void shouldAddDependencyInPomWithoutDependencyManagement() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaDependencyManagement(springBootDependencyManagement()));

      assertThat(contentNormalizingNewLines(pom)).contains(
        """
          <dependencyManagement>
            <dependencies>
              <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
              </dependency>
            </dependencies>
          </dependencyManagement>
        """
      );
    }

    @Test
    void shouldAddDependencyInPomWithDependencyManagement() {
      Path pom = projectWithPom("src/test/resources/projects/maven-empty-dependencies/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaDependencyManagement(springBootDependencyManagement()));

      assertThat(contentNormalizingNewLines(pom)).contains(
        """
              <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
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

      assertThat(contentNormalizingNewLines(pom)).contains(
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

    @Test
    void shouldAddDependencyManagementToMavenProfile() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(
        new AddJavaDependencyManagement(defaultVersionDependency(), localBuildProfile())
      );

      assertThat(contentNormalizingNewLines(pom)).contains(
        """
            <profile>
              <id>local</id>
              <dependencyManagement>
                <dependencies>
                  <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter</artifactId>
                  </dependency>
                </dependencies>
              </dependencyManagement>
            </profile>
        """
      );
    }
  }

  @Nested
  class HandleRemoveDirectJavaDependency {

    @Test
    void shouldNotRemoveUnknownDependency() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      assertThatCode(() ->
        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new RemoveDirectJavaDependency(jsonWebTokenDependencyId()))
      ).doesNotThrowAnyException();
    }

    @Test
    void shouldRemoveDependency() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new RemoveDirectJavaDependency(jsonWebTokenDependencyId()));

      assertThat(contentNormalizingNewLines(pom))
        .doesNotContain("      <groupId>io.jsonwebtoken</groupId>")
        .doesNotContain("      <artifactId>jjwt-api</artifactId>")
        .doesNotContain("      <version>${json-web-token.version}</version>")
        .doesNotContain("      <scope>test</scope>")
        .doesNotContain("      <optional>true</optional>");
    }

    @Test
    void shouldRemoveDependencyWithFullyMatchingId() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-multiple-webtoken/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new RemoveDirectJavaDependency(jsonWebTokenDependencyId()));

      assertThat(contentNormalizingNewLines(pom))
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
        .doesNotContain("      <version>${json-web-token.version}</version>")
        .doesNotContain("      <scope>test</scope>")
        .doesNotContain("      <optional>true</optional>");
    }

    @Test
    void shouldRemoveDependencyAndVersion() {
      Path pom = projectWithPom("src/test/resources/projects/maven-empty-dependencies/pom.xml");
      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
      mavenCommandHandler.handle(new SetVersion(springBootVersion()));
      mavenCommandHandler.handle(new AddDirectJavaDependency(optionalTestDependency()));

      mavenCommandHandler.handle(new RemoveDirectJavaDependency(dependencyId("org.junit.jupiter", "junit-jupiter-engine")));

      assertThat(contentNormalizingNewLines(pom))
        .doesNotContain("<spring-boot.version>")
        .doesNotContain("</spring-boot.version>")
        .doesNotContain(
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
    void shouldRemoveDependencyButKeepVersionIfStillUsed() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-multiple-dependencies/pom.xml");
      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);

      mavenCommandHandler.handle(new RemoveDirectJavaDependency(dependencyId("org.springframework.boot", "spring-boot-starter-web")));
      mavenCommandHandler.handle(
        new RemoveDirectJavaDependency(dependencyId("org.junit.jupiter", "junit-jupiter-engine"), localBuildProfile())
      );

      assertThat(contentNormalizingNewLines(pom))
        .doesNotContain(
          """
              <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
                <version>${spring-boot.version}</version>
              </dependency>
          """
        )
        .contains(
          """
              <profile>
                <id>local</id>
              </profile>
          """
        )
        .doesNotContain(
          """
                    <groupId>org.junit.jupiter</groupId>
                    <artifactId>junit-jupiter-engine</artifactId>
                    <version>${spring-boot.version}</version>
          """
        )
        .contains("<spring-boot.version>")
        .contains("</spring-boot.version>")
        .contains(
          """
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-data-jpa</artifactId>
                <version>${spring-boot.version}</version>
          """
        );
    }

    @Test
    void shouldRemoveDependencyFromProfile() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");
      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
      mavenCommandHandler.handle(new AddDirectJavaDependency(springBootStarterWebDependency(), localBuildProfile()));

      mavenCommandHandler.handle(
        new RemoveDirectJavaDependency(
          DependencyId.of(groupId("org.springframework.boot"), artifactId("spring-boot-starter-web")),
          localBuildProfile()
        )
      );

      assertThat(contentNormalizingNewLines(pom))
        .contains(
          """
              <profile>
                <id>local</id>
              </profile>
          """
        )
        .doesNotContain("      <groupId>org.springframework.boot</groupId>")
        .doesNotContain("      <artifactId>spring-boot-starter-web</artifactId>");
    }

    @Test
    void shouldRemoveDependencyAndVersionFromProfile() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");
      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
      mavenCommandHandler.handle(new SetVersion(springBootVersion()));
      mavenCommandHandler.handle(new AddDirectJavaDependency(optionalTestDependency(), localBuildProfile()));

      mavenCommandHandler.handle(
        new RemoveDirectJavaDependency(dependencyId("org.junit.jupiter", "junit-jupiter-engine"), localBuildProfile())
      );

      assertThat(contentNormalizingNewLines(pom))
        .contains(
          """
              <profile>
                <id>local</id>
              </profile>
          """
        )
        .doesNotContain("<spring-boot.version>")
        .doesNotContain("</spring-boot.version>")
        .doesNotContain(
          """
                  <groupId>org.junit.jupiter</groupId>
                  <artifactId>junit-jupiter-engine</artifactId>
                  <version>${spring-boot.version}</version>
          """
        );
    }

    @Test
    void shouldNotRemoveVersionFromProfileWhenRemovingDependencyWithoutProfile() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");
      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
      mavenCommandHandler.handle(new SetVersion(springBootVersion()));
      mavenCommandHandler.handle(new AddDirectJavaDependency(optionalTestDependency(), localBuildProfile()));

      mavenCommandHandler.handle(new RemoveDirectJavaDependency(dependencyId("org.junit.jupiter", "junit-jupiter-engine")));

      assertThat(contentNormalizingNewLines(pom))
        .contains("<spring-boot.version>")
        .contains("</spring-boot.version>")
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
    void shouldRemoveDependencyButKeepVersionIfStillUsedByProfile() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-multiple-dependencies/pom.xml");
      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);

      mavenCommandHandler.handle(new RemoveDirectJavaDependency(dependencyId("org.springframework.boot", "spring-boot-starter-web")));
      mavenCommandHandler.handle(new RemoveDirectJavaDependency(dependencyId("org.springframework.boot", "spring-boot-starter-data-jpa")));

      assertThat(contentNormalizingNewLines(pom))
        .doesNotContain(
          """
            <dependencies>
              <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
                <version>${spring-boot.version}</version>
              </dependency>
              <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-data-jpa</artifactId>
                <version>${spring-boot.version}</version>
              </dependency>
            </dependencies>
          """
        )
        .contains("<spring-boot.version>")
        .contains("</spring-boot.version>")
        .contains(
          """
                    <groupId>org.junit.jupiter</groupId>
                    <artifactId>junit-jupiter-engine</artifactId>
                    <version>${spring-boot.version}</version>
          """
        );
    }
  }

  @Nested
  class HandleRemoveAnnotationProcessor {

    @Test
    void shouldRemoveCompilerAnnotationProcessorPath() {
      Path pom = projectWithPom("src/test/resources/projects/init-maven/pom.xml");
      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
      mavenCommandHandler.handle(new AddJavaAnnotationProcessor(googleAutoServiceAnnotationProcessor()));
      mavenCommandHandler.handle(
        new AddJavaAnnotationProcessor(javaDependency().groupId("org.hibernate.orm").artifactId("auto-service").build())
      );
      mavenCommandHandler.handle(new AddJavaAnnotationProcessor(hibernateAnnotationProcessor()));

      mavenCommandHandler.handle(new RemoveJavaAnnotationProcessor(hibernateAnnotationProcessor().id()));

      assertThat(contentNormalizingNewLines(pom))
        .contains(
          // language=XML
          """
                      <path>
                        <groupId>com.google.auto.service</groupId>
                        <artifactId>auto-service</artifactId>
                        <version>${google-auto-service.version}</version>
                      </path>
          """
        )
        .doesNotContain(
          // language=XML
          """
                      <path>
                        <groupId>org.hibernate.orm</groupId>
                        <artifactId>hibernate-processor</artifactId>
                      </path>
          """
        );
    }

    @Test
    void shouldRemoveCompilerAnnotationProcessorPathsIfTheresNoRemainingAnnotationProcessorPaths() {
      Path pom = projectWithPom("src/test/resources/projects/init-maven/pom.xml");
      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
      mavenCommandHandler.handle(new AddJavaAnnotationProcessor(googleAutoServiceAnnotationProcessor()));

      mavenCommandHandler.handle(new RemoveJavaAnnotationProcessor(googleAutoServiceAnnotationProcessor().id()));

      assertThat(contentNormalizingNewLines(pom)).doesNotContain("<annotationProcessorPaths");
    }

    @Test
    void shouldRemoveCompilerAnnotationProcessorPathWithVersionToPluginManagementPlugins() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-plugin-management-compiler/pom.xml");

      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
      mavenCommandHandler.handle(new AddJavaAnnotationProcessor(googleAutoServiceAnnotationProcessor()));

      mavenCommandHandler.handle(new RemoveJavaAnnotationProcessor(googleAutoServiceAnnotationProcessor().id()));

      assertThat(contentNormalizingNewLines(pom)).doesNotContain(
        """
                    <annotationProcessorPaths>
                      <path>
                        <groupId>com.google.auto.service</groupId>
                        <artifactId>auto-service</artifactId>
                        <version>${google-auto-service.version}</version>
                      </path>
                    </annotationProcessorPaths>
        """
      );
    }
  }

  @Nested
  class HandleAddDirectJavaDependency {

    @Test
    void shouldAddDependencyInPomWithoutDependencies() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddDirectJavaDependency(optionalTestDependency()));

      assertThat(contentNormalizingNewLines(pom)).contains(
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

      String content = contentNormalizingNewLines(pom);
      assertThat(content).contains(
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

      String content = contentNormalizingNewLines(pom);
      assertThat(content).contains(
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

      assertThat(contentNormalizingNewLines(pom)).contains(
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

      assertThat(contentNormalizingNewLines(pom)).contains(
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

      assertThat(contentNormalizingNewLines(pom)).contains(
        """
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter</artifactId>
            </dependency>
          </dependencies>
        """
      );
    }

    @Test
    void shouldAddDependencyToMavenProfile() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(
        new AddDirectJavaDependency(defaultVersionDependency(), localBuildProfile())
      );

      assertThat(contentNormalizingNewLines(pom)).contains(
        """
            <profile>
              <id>local</id>
              <dependencies>
                <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter</artifactId>
                </dependency>
              </dependencies>
            </profile>
        """
      );
    }
  }

  @Nested
  class HandleAddAnnotationProcessor {

    @Test
    void shouldAddCompilerAnnotationProcessorPathWithoutVersionToPlugins() {
      Path pom = projectWithPom("src/test/resources/projects/init-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaAnnotationProcessor(hibernateAnnotationProcessor()));

      assertThat(contentNormalizingNewLines(pom)).contains(
        // language=XML
        """
              <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${compiler-plugin.version}</version>
                <configuration>
                  <release>${java.version}</release>
                  <annotationProcessorPaths>
                    <path>
                      <groupId>org.hibernate.orm</groupId>
                      <artifactId>hibernate-processor</artifactId>
                    </path>
                  </annotationProcessorPaths>
                </configuration>
              </plugin>
        """
      );
    }

    @Test
    void shouldAddCompilerAnnotationProcessorPathWithVersionToPlugins() {
      Path pom = projectWithPom("src/test/resources/projects/init-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaAnnotationProcessor(googleAutoServiceAnnotationProcessor()));

      assertThat(contentNormalizingNewLines(pom)).contains(
        """
              <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${compiler-plugin.version}</version>
                <configuration>
                  <release>${java.version}</release>
                  <annotationProcessorPaths>
                    <path>
                      <groupId>com.google.auto.service</groupId>
                      <artifactId>auto-service</artifactId>
                      <version>${google-auto-service.version}</version>
                    </path>
                  </annotationProcessorPaths>
                </configuration>
              </plugin>
        """
      );
    }

    @Test
    void shouldAddCompilerAnnotationProcessorPathWithVersionToPluginManagementPlugins() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-plugin-management-compiler/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaAnnotationProcessor(googleAutoServiceAnnotationProcessor()));

      assertThat(contentNormalizingNewLines(pom)).contains(
        """
                <plugin>
                  <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-compiler-plugin</artifactId>
                  <configuration>
                    <annotationProcessorPaths>
                      <path>
                        <groupId>com.google.auto.service</groupId>
                        <artifactId>auto-service</artifactId>
                        <version>${google-auto-service.version}</version>
                      </path>
                    </annotationProcessorPaths>
                  </configuration>
                  <version>${compiler-plugin.version}</version>
                </plugin>
        """
      );
    }

    @Test
    void shouldFailIfCompilerPluginIsNotDeclared() {
      Path pom = projectWithPom("src/main/resources/generator/buildtool/maven/pom.xml.mustache");
      MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);

      assertThatThrownBy(() -> mavenCommandHandler.handle(new AddJavaAnnotationProcessor(googleAutoServiceAnnotationProcessor())))
        .isInstanceOf(GeneratorException.class)
        .hasMessageContaining("maven-compiler-plugin should already be declared to add annotation processor dependencies");
    }
  }

  @Nested
  class HandleAddMavenPluginManagement {

    @Nested
    class WithoutProfile {

      @Test
      void shouldHandleMalformedConfiguration() {
        Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

        AddMavenPluginManagement command = AddMavenPluginManagement.builder()
          .plugin(mavenPlugin().groupId("org.apache.maven.plugins").artifactId("maven-enforcer-plugin").configuration("<dummy").build())
          .build();
        assertThatThrownBy(() -> new MavenCommandHandler(Indentation.DEFAULT, pom).handle(command)).isExactlyInstanceOf(
          MalformedAdditionalInformationException.class
        );
      }

      @Test
      void shouldAddBuildPluginManagementToEmptyPom() {
        Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

        addMavenEnforcerPlugin(pom);

        assertThat(contentNormalizingNewLines(pom)).contains(pluginManagement());
      }

      @Test
      void shouldAddPropertyForPluginVersion() {
        Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(
          AddMavenPluginManagement.builder().plugin(mavenEnforcerPlugin()).pluginVersion(mavenEnforcerVersion()).build()
        );

        assertThat(contentNormalizingNewLines(pom)).contains("<maven-enforcer-plugin.version>1.1.1</maven-enforcer-plugin.version>");
      }

      @Test
      void shouldAddPropertyForPluginDependencyVersion() {
        Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

        AddMavenPluginManagement command = AddMavenPluginManagement.builder()
          .plugin(mavenEnforcerPluginManagement())
          .addDependencyVersion(new JavaDependencyVersion("json-web-token", "1.1.1"))
          .build();
        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(command);

        assertThat(contentNormalizingNewLines(pom)).contains("<json-web-token.version>1.1.1</json-web-token.version>");
      }

      @Test
      void shouldAddBuildPluginManagementToPomWithoutPluginManagement() {
        Path pom = projectWithPom("src/test/resources/projects/maven-empty-build/pom.xml");

        addMavenEnforcerPlugin(pom);

        assertThat(contentNormalizingNewLines(pom))
          .contains(pluginManagement())
          .doesNotContain(
            """
              <build>
              </build>
            """
          );
      }

      @Test
      void shouldAddBuildPluginManagementToPomWithEmptyPluginManagement() {
        Path pom = projectWithPom("src/test/resources/projects/maven-empty-plugin-management/pom.xml");

        addMavenEnforcerPlugin(pom);

        assertThat(contentNormalizingNewLines(pom))
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

        assertThat(contentNormalizingNewLines(pom)).contains(pluginManagement());
      }

      @Test
      void shouldReplaceExistingMavenPlugin() {
        Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");
        MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
        mavenCommandHandler.handle(AddMavenPluginManagement.builder().plugin(mavenEnforcerPlugin()).build());

        mavenCommandHandler.handle(
          AddMavenPluginManagement.builder()
            .plugin(
              mavenPlugin()
                .groupId("org.apache.maven.plugins")
                .artifactId("maven-enforcer-plugin")
                .versionSlug("maven-enforcer-plugin")
                .build()
            )
            .build()
        );

        assertThat(contentNormalizingNewLines(pom))
          .doesNotContain(
            // language=xml
            """
                    <plugin>
                      <artifactId>maven-enforcer-plugin</artifactId>
                    </plugin>\
            """
          )
          .containsOnlyOnce(
            // language=xml
            """
                    <plugin>
                      <artifactId>maven-enforcer-plugin</artifactId>
                      <version>${maven-enforcer-plugin.version}</version>
                    </plugin>\
            """
          );
      }

      @Test
      void shouldReplaceExistingThirdPartyPlugin() {
        Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");
        MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
        mavenCommandHandler.handle(AddMavenPluginManagement.builder().plugin(asciidoctorPlugin()).build());
        mavenCommandHandler.handle(AddMavenPluginManagement.builder().plugin(mavenEnforcerPlugin()).build());

        mavenCommandHandler.handle(
          AddMavenPluginManagement.builder()
            .plugin(mavenPlugin().groupId("org.asciidoctor").artifactId("asciidoctor-maven-plugin").build())
            .build()
        );

        assertThat(contentNormalizingNewLines(pom))
          .doesNotContain(
            // language=xml
            """
                    <plugin>
                      <groupId>org.asciidoctor</groupId>
                      <artifactId>asciidoctor-maven-plugin</artifactId>
                      <dependencies>
                        <dependency>
                          <groupId>org.asciidoctor</groupId>
                          <artifactId>asciidoctorj-screenshot</artifactId>
                        </dependency>
                        <dependency>
                          <groupId>org.asciidoctor</groupId>
                          <artifactId>asciidoctorj-diagram</artifactId>
                        </dependency>
                      </dependencies>
                    </plugin>\
            """
          )
          .containsOnlyOnce(
            // language=xml
            """
                    <plugin>
                      <groupId>org.asciidoctor</groupId>
                      <artifactId>asciidoctor-maven-plugin</artifactId>
                    </plugin>\
            """
          );
      }

      private void addMavenEnforcerPlugin(Path pom) {
        AddMavenPluginManagement command = AddMavenPluginManagement.builder().plugin(mavenEnforcerPluginManagement()).build();
        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(command);
      }

      private String pluginManagement() {
        return """
                <plugin>
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
                      <goals>
                        <goal>enforce</goal>
                      </goals>
                      <configuration>
                        <rules>
                          <DependencyConvergence />
                        </rules>
                        <fail>false</fail>
                      </configuration>
                    </execution>
                  </executions>
                  <dependencies>
                    <dependency>
                      <groupId>io.jsonwebtoken</groupId>
                      <artifactId>jjwt-jackson</artifactId>
                      <version>${json-web-token.version}</version>
                      <exclusions>
                        <exclusion>
                          <groupId>com.fasterxml.jackson.core</groupId>
                          <artifactId>jackson-databind</artifactId>
                        </exclusion>
                      </exclusions>
                    </dependency>
                  </dependencies>
                  <configuration>
                    <rules>
                      <requireMavenVersion>
                        <message>You are running an older version of Maven. Seed4J requires at least Maven ${maven.version}</message>
                        <version>[${maven.version},)</version>
                      </requireMavenVersion>
                      <requireJavaVersion>
                        <message>You are running an incompatible version of Java. Seed4J engine supports JDK 25+.</message>
                        <version>[${java.version},)</version>
                      </requireJavaVersion>
                    </rules>
                  </configuration>
                </plugin>
              </plugins>
        """;
      }
    }

    @Nested
    class WithProfile {

      @Test
      void shouldNotAddBuildPluginToEmptyPom() {
        Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

        assertThatThrownBy(() -> addMavenEnforcerPlugin(pom)).isExactlyInstanceOf(MissingMavenProfileException.class);
      }

      @Test
      void shouldAddPropertyForPluginVersion() {
        Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");

        addMavenEnforcerPlugin(pom);

        assertThat(contentNormalizingNewLines(pom)).contains("<maven-enforcer-plugin.version>1.1.1</maven-enforcer-plugin.version>");
      }

      @Test
      void shouldAddBuildPluginToPomWithPlugins() {
        Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");

        addMavenEnforcerPlugin(pom);

        assertThat(contentNormalizingNewLines(pom)).contains(plugins());
      }

      private void addMavenEnforcerPlugin(Path pom) {
        AddMavenPluginManagement command = AddMavenPluginManagement.builder()
          .plugin(mavenEnforcerPluginManagement())
          .pluginVersion(mavenEnforcerVersion())
          .buildProfile(localBuildProfile())
          .build();
        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(command);
      }

      private String plugins() {
        return """
            <profile>
              <id>local</id>
              <build>
                <pluginManagement>
                  <plugins>
                    <plugin>
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
                          <goals>
                            <goal>enforce</goal>
                          </goals>
                          <configuration>
                            <rules>
                              <DependencyConvergence />
                            </rules>
                            <fail>false</fail>
                          </configuration>
                        </execution>
                      </executions>
                      <dependencies>
                        <dependency>
                          <groupId>io.jsonwebtoken</groupId>
                          <artifactId>jjwt-jackson</artifactId>
                          <version>${json-web-token.version}</version>
                          <exclusions>
                            <exclusion>
                              <groupId>com.fasterxml.jackson.core</groupId>
                              <artifactId>jackson-databind</artifactId>
                            </exclusion>
                          </exclusions>
                        </dependency>
                      </dependencies>
                      <configuration>
                        <rules>
                          <requireMavenVersion>
                            <message>You are running an older version of Maven. Seed4J requires at least Maven ${maven.version}</message>
                            <version>[${maven.version},)</version>
                          </requireMavenVersion>
                          <requireJavaVersion>
                            <message>You are running an incompatible version of Java. Seed4J engine supports JDK 25+.</message>
                            <version>[${java.version},)</version>
                          </requireJavaVersion>
                        </rules>
                      </configuration>
                    </plugin>
                  </plugins>
                </pluginManagement>
              </build>
            </profile>
        """;
      }
    }
  }

  @Nested
  class HandleAddDirectMavenPlugin {

    @Nested
    class WithoutProfile {

      @Test
      void shouldAddBuildPluginToEmptyPom() {
        Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

        addMavenEnforcerPlugin(pom);

        assertThat(contentNormalizingNewLines(pom)).contains(plugins());
      }

      @Test
      void shouldAddPropertyForPluginVersion() {
        Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(
          AddDirectMavenPlugin.builder().plugin(mavenEnforcerPlugin()).pluginVersion(mavenEnforcerVersion()).build()
        );

        assertThat(contentNormalizingNewLines(pom)).contains("<maven-enforcer-plugin.version>1.1.1</maven-enforcer-plugin.version>");
      }

      @Test
      void shouldAddPropertyForPluginDependencyVersion() {
        Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

        AddDirectMavenPlugin command = AddDirectMavenPlugin.builder()
          .plugin(mavenEnforcerPluginManagement())
          .addDependencyVersion(new JavaDependencyVersion("json-web-token", "1.1.1"))
          .build();
        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(command);

        assertThat(contentNormalizingNewLines(pom)).contains("<json-web-token.version>1.1.1</json-web-token.version>");
      }

      @Test
      void shouldAddBuildPluginToPomWithEmptyBuild() {
        Path pom = projectWithPom("src/test/resources/projects/maven-empty-build/pom.xml");

        addMavenEnforcerPlugin(pom);

        assertThat(contentNormalizingNewLines(pom))
          .contains(plugins())
          .doesNotContain(
            """
              <build>
              </build>
            """
          );
      }

      @Test
      void shouldAddBuildPluginToPomWithPlugins() {
        Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

        addMavenEnforcerPlugin(pom);

        assertThat(contentNormalizingNewLines(pom))
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

      @Test
      void shouldReplaceExistingMavenPlugin() {
        Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");
        MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
        mavenCommandHandler.handle(AddDirectMavenPlugin.builder().plugin(mavenEnforcerPlugin()).build());
        mavenCommandHandler.handle(AddDirectMavenPlugin.builder().plugin(asciidoctorPlugin()).build());

        mavenCommandHandler.handle(
          AddDirectMavenPlugin.builder()
            .plugin(
              mavenPlugin()
                .groupId("org.apache.maven.plugins")
                .artifactId("maven-enforcer-plugin")
                .versionSlug("maven-enforcer-plugin")
                .build()
            )
            .build()
        );

        assertThat(contentNormalizingNewLines(pom))
          .doesNotContain(
            // language=xml
            """
                  <plugin>
                    <artifactId>maven-enforcer-plugin</artifactId>
                  </plugin>\
            """
          )
          .containsOnlyOnce(
            // language=xml
            """
                  <plugin>
                    <artifactId>maven-enforcer-plugin</artifactId>
                    <version>${maven-enforcer-plugin.version}</version>
                  </plugin>\
            """
          );
      }

      @Test
      void shouldReplaceExistingThirdPartyPlugin() {
        Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");
        MavenCommandHandler mavenCommandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
        mavenCommandHandler.handle(AddDirectMavenPlugin.builder().plugin(asciidoctorPlugin()).build());

        mavenCommandHandler.handle(
          AddDirectMavenPlugin.builder()
            .plugin(mavenPlugin().groupId("org.asciidoctor").artifactId("asciidoctor-maven-plugin").build())
            .build()
        );

        assertThat(contentNormalizingNewLines(pom))
          .doesNotContain(
            // language=xml
            """
                  <plugin>
                    <groupId>org.asciidoctor</groupId>
                    <artifactId>asciidoctor-maven-plugin</artifactId>
                    <dependencies>
                      <dependency>
                        <groupId>org.asciidoctor</groupId>
                        <artifactId>asciidoctorj-screenshot</artifactId>
                      </dependency>
                      <dependency>
                        <groupId>org.asciidoctor</groupId>
                        <artifactId>asciidoctorj-diagram</artifactId>
                      </dependency>
                    </dependencies>
                  </plugin>\
            """
          )
          .containsOnlyOnce(
            // language=xml
            """
                  <plugin>
                    <groupId>org.asciidoctor</groupId>
                    <artifactId>asciidoctor-maven-plugin</artifactId>
                  </plugin>\
            """
          );
      }

      private void addMavenEnforcerPlugin(Path pom) {
        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(
          AddDirectMavenPlugin.builder().plugin(mavenEnforcerPluginManagement()).build()
        );
      }

      private String plugins() {
        return """
              <plugin>
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
                    <goals>
                      <goal>enforce</goal>
                    </goals>
                    <configuration>
                      <rules>
                        <DependencyConvergence />
                      </rules>
                      <fail>false</fail>
                    </configuration>
                  </execution>
                </executions>
                <dependencies>
                  <dependency>
                    <groupId>io.jsonwebtoken</groupId>
                    <artifactId>jjwt-jackson</artifactId>
                    <version>${json-web-token.version}</version>
                    <exclusions>
                      <exclusion>
                        <groupId>com.fasterxml.jackson.core</groupId>
                        <artifactId>jackson-databind</artifactId>
                      </exclusion>
                    </exclusions>
                  </dependency>
                </dependencies>
                <configuration>
                  <rules>
                    <requireMavenVersion>
                      <message>You are running an older version of Maven. Seed4J requires at least Maven ${maven.version}</message>
                      <version>[${maven.version},)</version>
                    </requireMavenVersion>
                    <requireJavaVersion>
                      <message>You are running an incompatible version of Java. Seed4J engine supports JDK 25+.</message>
                      <version>[${java.version},)</version>
                    </requireJavaVersion>
                  </rules>
                </configuration>
              </plugin>
            </plugins>
        """;
      }
    }

    @Nested
    class WithProfile {

      @Test
      void shouldNotAddBuildPluginToEmptyPom() {
        Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

        assertThatThrownBy(() -> addMavenEnforcerPlugin(pom)).isExactlyInstanceOf(MissingMavenProfileException.class);
      }

      @Test
      void shouldAddPropertyForPluginVersion() {
        Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");

        addMavenEnforcerPlugin(pom);

        assertThat(contentNormalizingNewLines(pom)).contains("<maven-enforcer-plugin.version>1.1.1</maven-enforcer-plugin.version>");
      }

      @Test
      void shouldAddBuildPluginToPomWithPlugins() {
        Path pom = projectWithPom("src/test/resources/projects/maven-with-local-profile/pom.xml");

        addMavenEnforcerPlugin(pom);

        assertThat(contentNormalizingNewLines(pom)).contains(plugins());
      }

      private void addMavenEnforcerPlugin(Path pom) {
        new MavenCommandHandler(Indentation.DEFAULT, pom).handle(
          AddDirectMavenPlugin.builder()
            .plugin(mavenEnforcerPluginManagement())
            .pluginVersion(mavenEnforcerVersion())
            .buildProfile(localBuildProfile())
            .build()
        );
      }

      private String plugins() {
        return """
            <profile>
              <id>local</id>
              <build>
                <plugins>
                  <plugin>
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
                        <goals>
                          <goal>enforce</goal>
                        </goals>
                        <configuration>
                          <rules>
                            <DependencyConvergence />
                          </rules>
                          <fail>false</fail>
                        </configuration>
                      </execution>
                    </executions>
                    <dependencies>
                      <dependency>
                        <groupId>io.jsonwebtoken</groupId>
                        <artifactId>jjwt-jackson</artifactId>
                        <version>${json-web-token.version}</version>
                        <exclusions>
                          <exclusion>
                            <groupId>com.fasterxml.jackson.core</groupId>
                            <artifactId>jackson-databind</artifactId>
                          </exclusion>
                        </exclusions>
                      </dependency>
                    </dependencies>
                    <configuration>
                      <rules>
                        <requireMavenVersion>
                          <message>You are running an older version of Maven. Seed4J requires at least Maven ${maven.version}</message>
                          <version>[${maven.version},)</version>
                        </requireMavenVersion>
                        <requireJavaVersion>
                          <message>You are running an incompatible version of Java. Seed4J engine supports JDK 25+.</message>
                          <version>[${java.version},)</version>
                        </requireJavaVersion>
                      </rules>
                    </configuration>
                  </plugin>
                </plugins>
              </build>
            </profile>
        """;
      }
    }
  }

  @Nested
  class HandleAddMavenBuildExtension {

    @Test
    void shouldAddBuildExtensionInPomWithoutBuild() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddMavenBuildExtension(mavenBuildExtensionWithSlug()));

      assertThat(contentNormalizingNewLines(pom)).contains(
        """
            <extensions>
              <extension>
                <groupId>kr.motd.maven</groupId>
                <artifactId>os-maven-plugin</artifactId>
                <version>${os-maven-plugin.version}</version>
              </extension>
            </extensions>
        """
      );
    }

    @Test
    void shouldAddBuildExtensionInPomWithoutExtensions() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddMavenBuildExtension(mavenBuildExtensionWithSlug()));

      String content = contentNormalizingNewLines(pom);
      assertThat(content).contains(
        """
            <extensions>
              <extension>
                <groupId>kr.motd.maven</groupId>
                <artifactId>os-maven-plugin</artifactId>
                <version>${os-maven-plugin.version}</version>
              </extension>
            </extensions>
        """
      );

      assertThat(Pattern.compile("^ +$", Pattern.MULTILINE).matcher(content).find()).isFalse();
    }

    @Test
    void shouldAddBuildExtensionInPomWithExtensions() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-extensions/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddMavenBuildExtension(mavenBuildExtensionWithSlug()));

      String content = contentNormalizingNewLines(pom);
      assertThat(content).contains(
        """
          <build>
            <extensions>
              <extension>
                <groupId>io.opentelemetry.contrib</groupId>
                <artifactId>opentelemetry-maven-extension</artifactId>
                <version>1.18.0</version>
              </extension>
              <extension>
                <groupId>kr.motd.maven</groupId>
                <artifactId>os-maven-plugin</artifactId>
                <version>${os-maven-plugin.version}</version>
              </extension>
            </extensions>
        """
      );

      assertThat(Pattern.compile("^ +$", Pattern.MULTILINE).matcher(content).find()).isFalse();
    }
  }

  @Test
  void addGradlePluginCommandShouldNotBeHandled() {
    Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

    MavenCommandHandler commandHandler = new MavenCommandHandler(Indentation.DEFAULT, pom);
    AddGradlePlugin command = AddGradlePlugin.builder().plugin(checkstyleGradlePlugin()).build();
    assertThatCode(() -> commandHandler.handle(command)).doesNotThrowAnyException();
  }

  private static Path projectWithPom(String sourcePom) {
    Path folder = Path.of(tmpDirForTest());

    try {
      Files.createDirectories(folder);
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    Path pomPath = folder.resolve("pom.xml");
    try {
      Files.copy(Path.of(sourcePom), pomPath);
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    return pomPath;
  }
}
