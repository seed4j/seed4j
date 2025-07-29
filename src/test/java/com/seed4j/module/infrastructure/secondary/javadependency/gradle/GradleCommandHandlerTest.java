package com.seed4j.module.infrastructure.secondary.javadependency.gradle;

import static com.seed4j.TestFileUtils.*;
import static com.seed4j.module.domain.JHipsterModule.*;
import static com.seed4j.module.domain.JHipsterModulesFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.EnumSource.Mode.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.buildproperties.BuildProperty;
import com.seed4j.module.domain.buildproperties.PropertyKey;
import com.seed4j.module.domain.buildproperties.PropertyValue;
import com.seed4j.module.domain.file.TemplateRenderer;
import com.seed4j.module.domain.gradleplugin.GradlePlugin;
import com.seed4j.module.domain.javabuild.command.*;
import com.seed4j.module.domain.javabuildprofile.BuildProfileActivation;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.javadependency.JavaDependencyType;
import com.seed4j.module.domain.javadependency.JavaDependencyVersion;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.module.infrastructure.secondary.FileSystemJHipsterModuleFiles;
import com.seed4j.module.infrastructure.secondary.FileSystemProjectFiles;
import com.seed4j.module.infrastructure.secondary.FileSystemReplacer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GradleCommandHandlerTest {

  private static final FileSystemJHipsterModuleFiles files = new FileSystemJHipsterModuleFiles(
    new FileSystemProjectFiles(),
    TemplateRenderer.NOOP
  );
  private static final FileSystemReplacer fileReplacer = new FileSystemReplacer(TemplateRenderer.NOOP);

  @Test
  void shouldHandleInvalidTomlVersionCatalog() {
    JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-unreadable");

    assertThatThrownBy(() ->
      new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer)
    ).isExactlyInstanceOf(InvalidTomlVersionCatalogException.class);
  }

  @Nested
  class HandleSetVersion {

    @Test
    void shouldAddVersionToMissingTomlVersionCatalogAnd() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty");

      new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new SetVersion(springBootVersion())
      );

      assertThat(versionCatalogContent(projectFolder)).contains(
          """
          [versions]
          \tspring-boot = "1.2.3"
          """
        );
    }

    @Test
    void shouldAddVersionToExistingTomlVersionCatalog() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");

      new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new SetVersion(springBootVersion())
      );

      assertThat(versionCatalogContent(projectFolder)).contains(
          """
          [versions]
          \tspring-boot = "1.2.3"
          """
        );
    }

    @Test
    void shouldUpdateExistingProperty() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        projectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new SetVersion(new JavaDependencyVersion("jjwt", "0.12.0")));

      gradleCommandHandler.handle(new SetVersion(new JavaDependencyVersion("jjwt", "0.13.0")));

      assertThat(versionCatalogContent(projectFolder)).contains(
          """
          [versions]
          \tjjwt = "0.13.0"
          """
        );
    }
  }

  @Nested
  class HandleSetBuildProperty {

    @Nested
    class WithoutProfile {

      @Test
      void shouldAddPropertiesToBuildGradleFileWithoutProperties() {
        JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");

        new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
          new SetBuildProperty(new BuildProperty(new PropertyKey("spring-profiles-active"), new PropertyValue("local")))
        );

        assertThat(buildGradleContent(projectFolder)).contains(
            """
            val springProfilesActive by extra("local")
            // jhipster-needle-gradle-properties
            """
          );
      }

      @Test
      void shouldAddPropertiesToBuildGradleFileWithProperties() {
        JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-properties");

        new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
          new SetBuildProperty(springProfilesActiveProperty())
        );

        assertThat(buildGradleContent(projectFolder)).contains(
            """
            val javaVersion by extra("21")
            val springProfilesActive by extra("local")
            // jhipster-needle-gradle-properties
            """
          );
      }

      @Test
      void shouldUpdateExistingProperty() {
        JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-properties");
        GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
          Indentation.DEFAULT,
          projectFolder,
          emptyModuleContext(),
          files,
          fileReplacer
        );
        gradleCommandHandler.handle(new SetBuildProperty(springProfilesActiveProperty()));

        gradleCommandHandler.handle(
          new SetBuildProperty(new BuildProperty(new PropertyKey("spring.profiles.active"), new PropertyValue("dev")))
        );

        assertThat(buildGradleContent(projectFolder))
          .contains(
            """
            val springProfilesActive by extra("dev")
            // jhipster-needle-gradle-properties
            """
          )
          .doesNotContain(
            """
            val springProfilesActive by extra("local")\
            """
          );
      }

      @Test
      void shouldNotUpdateExistingPropertyWithSameValue() {
        JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-properties");
        GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
          Indentation.DEFAULT,
          projectFolder,
          emptyModuleContext(),
          files,
          fileReplacer
        );
        gradleCommandHandler.handle(new SetBuildProperty(springProfilesActiveProperty()));

        gradleCommandHandler.handle(new SetBuildProperty(springProfilesActiveProperty()));

        assertThat(buildGradleContent(projectFolder)).contains(
            """
            val springProfilesActive by extra("local")
            // jhipster-needle-gradle-properties
            """
          );
      }
    }

    @Nested
    class WithProfile {

      @Test
      void shouldNotAddPropertiesToGradleWithoutBuildGradleProfileFile() {
        JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");

        assertThatThrownBy(() ->
          new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
            new SetBuildProperty(springProfilesActiveProperty(), localBuildProfile())
          )
        ).isExactlyInstanceOf(MissingGradleProfileException.class);
      }

      @Test
      void shouldAddPropertiesToBuildGradleProfileFileWithoutProperties() {
        JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile");

        new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
          new SetBuildProperty(springProfilesActiveProperty(), localBuildProfile())
        );

        assertThat(scriptPluginContent(projectFolder, localBuildProfile())).contains(
            """
            val springProfilesActive by extra("local")
            // jhipster-needle-gradle-properties
            """
          );
      }

      @Test
      void shouldAddPropertiesToBuildGradleProfileFileWithProperties() {
        JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile-and-properties");

        new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
          new SetBuildProperty(springProfilesActiveProperty(), localBuildProfile())
        );

        assertThat(scriptPluginContent(projectFolder, localBuildProfile())).contains(
            """
            val javaVersion by extra("21")
            val springProfilesActive by extra("local")
            // jhipster-needle-gradle-properties
            """
          );
      }

      @Test
      void shouldUpdateExistingProfileProperty() {
        JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile-and-properties");
        GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
          Indentation.DEFAULT,
          projectFolder,
          emptyModuleContext(),
          files,
          fileReplacer
        );
        gradleCommandHandler.handle(new SetBuildProperty(springProfilesActiveProperty(), localBuildProfile()));

        gradleCommandHandler.handle(
          new SetBuildProperty(new BuildProperty(new PropertyKey("spring.profiles.active"), new PropertyValue("dev")), localBuildProfile())
        );

        assertThat(scriptPluginContent(projectFolder, localBuildProfile()))
          .contains(
            """
            val springProfilesActive by extra("dev")
            // jhipster-needle-gradle-properties
            """
          )
          .doesNotContain(
            """
            val springProfilesActive by extra("local")\
            """
          );
      }

      @Test
      void shouldNotUpdateExistingProfilePropertyWithSameValue() {
        JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile-and-properties");
        GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
          Indentation.DEFAULT,
          projectFolder,
          emptyModuleContext(),
          files,
          fileReplacer
        );
        gradleCommandHandler.handle(new SetBuildProperty(springProfilesActiveProperty(), localBuildProfile()));

        gradleCommandHandler.handle(new SetBuildProperty(springProfilesActiveProperty(), localBuildProfile()));

        assertThat(scriptPluginContent(projectFolder, localBuildProfile())).contains(
            """
            val springProfilesActive by extra("local")
            // jhipster-needle-gradle-properties
            """
          );
      }
    }
  }

  @Nested
  class HandleAddJavaBuildProfile {

    @Test
    void shouldEnablePrecompiledScriptPluginsToBuildGradleFile() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");

      new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddJavaBuildProfile(buildProfileId("local"))
      );

      assertFileExists(projectFolder, "buildSrc/build.gradle.kts", "The file build.gradle.kts should exist at %s");
    }

    @Test
    void shouldInjectTomlVersionCatalogLibsIntoBuildGradleFileWithProfiles() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");

      new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddJavaBuildProfile(buildProfileId("local"))
      );

      assertFileExists(projectFolder, "buildSrc/settings.gradle.kts", "The file settings.gradle.kts should exist at %s");
    }

    @Test
    void shouldAddProfileWithIdOnlyToBuildGradleFileWithoutProfiles() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");

      new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddJavaBuildProfile(buildProfileId("local"))
      );

      assertThat(buildGradleContent(projectFolder)).contains(
          """
          if (profiles.contains("local")) {
            apply(plugin = "profile-local")
          }
          // jhipster-needle-profile-activation\
          """
        );
      assertFileExists(
        projectFolder,
        "buildSrc/src/main/kotlin/profile-local.gradle.kts",
        "The file profile-local.gradle.kts should exist at %s"
      );
    }

    @Test
    void shouldAddProfileWithIdOnlyToBuildGradleFileWithProfiles() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        projectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );

      gradleCommandHandler.handle(new AddJavaBuildProfile(buildProfileId("local")));
      gradleCommandHandler.handle(new AddJavaBuildProfile(buildProfileId("dev")));

      assertThat(buildGradleContent(projectFolder)).contains(
          """
          if (profiles.contains("local")) {
            apply(plugin = "profile-local")
          }
          if (profiles.contains("dev")) {
            apply(plugin = "profile-dev")
          }
          // jhipster-needle-profile-activation\
          """
        );
      assertFileExists(
        projectFolder,
        "buildSrc/src/main/kotlin/profile-local.gradle.kts",
        "The file profile-local.gradle.kts should exist at %s"
      );
    }

    @Test
    void shouldNotDuplicateExistingProfile() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        projectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );

      gradleCommandHandler.handle(new AddJavaBuildProfile(buildProfileId("local")));
      gradleCommandHandler.handle(new AddJavaBuildProfile(buildProfileId("local")));

      assertThat(buildGradleContent(projectFolder)).contains(
          """
          val profiles = (project.findProperty("profiles") as String? ?: "")
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
          if (profiles.contains("local")) {
            apply(plugin = "profile-local")
          }
          // jhipster-needle-profile-activation\
          """
        );
      assertFileExists(
        projectFolder,
        "buildSrc/src/main/kotlin/profile-local.gradle.kts",
        "The file profile-local.gradle.kts should exist at %s"
      );
    }

    @ParameterizedTest
    @MethodSource("provideBuildProfileActivations")
    void shouldNotAddDefaultActivationToBuildGradleFile(BuildProfileActivation profileActivation) {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");

      new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddJavaBuildProfile(buildProfileId("local"), profileActivation)
      );

      assertThat(buildGradleContent(projectFolder)).contains(
          """
          if (profiles.contains("local")) {
            apply(plugin = "profile-local")
          }
          // jhipster-needle-profile-activation\
          """
        );
    }

    private static Stream<BuildProfileActivation> provideBuildProfileActivations() {
      return Stream.of(BuildProfileActivation.builder().build(), BuildProfileActivation.builder().activeByDefault(false).build());
    }

    @Test
    void shouldAddDefaultActivationWithActivationByDefaultTrueToBuildGradleFile() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");

      new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddJavaBuildProfile(buildProfileId("local"), BuildProfileActivation.builder().activeByDefault().build())
      );

      assertThat(buildGradleContent(projectFolder)).contains(
          """
          if (profiles.isEmpty() || profiles.contains("local")) {
            apply(plugin = "profile-local")
          }
          // jhipster-needle-profile-activation\
          """
        );
    }

    @Test
    void shouldNotDuplicateExistingProfileWithDifferentActivation() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        projectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );

      gradleCommandHandler.handle(
        new AddJavaBuildProfile(buildProfileId("local"), BuildProfileActivation.builder().activeByDefault(false).build())
      );
      gradleCommandHandler.handle(
        new AddJavaBuildProfile(buildProfileId("local"), BuildProfileActivation.builder().activeByDefault(true).build())
      );

      assertThat(buildGradleContent(projectFolder)).contains(
          """
          val profiles = (project.findProperty("profiles") as String? ?: "")
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
          if (profiles.contains("local")) {
            apply(plugin = "profile-local")
          }
          // jhipster-needle-profile-activation\
          """
        );
      assertFileExists(
        projectFolder,
        "buildSrc/src/main/kotlin/profile-local.gradle.kts",
        "The file profile-local.gradle.kts should exist at %s"
      );
    }
  }

  @Nested
  class HandleAddDirectJavaDependency {

    private final JHipsterProjectFolder emptyGradleProjectFolder = projectFrom("src/test/resources/projects/empty-gradle");

    @Test
    void shouldAddEntryInLibrariesSectionToExistingTomlVersionCatalog() {
      new GradleCommandHandler(Indentation.DEFAULT, emptyGradleProjectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddDirectJavaDependency(springBootStarterWebDependency())
      );

      assertThat(versionCatalogContent(emptyGradleProjectFolder)).contains(
          """
          [libraries.spring-boot-starter-web]
          \t\tname = "spring-boot-starter-web"
          \t\tgroup = "org.springframework.boot"
          """
        );
    }

    @Test
    void shouldUpdateEntryInLibrariesSectionToExistingTomlVersionCatalog() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      JavaDependency initialDependency = javaDependency().groupId("org.springframework.boot").artifactId("spring-boot-starter-web").build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(initialDependency));

      JavaDependency updatedDependency = javaDependency().groupId("org.spring.boot").artifactId("spring-boot-starter-web").build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(updatedDependency));

      assertThat(versionCatalogContent(emptyGradleProjectFolder)).contains(
          """
          [libraries.spring-boot-starter-web]
          \t\tname = "spring-boot-starter-web"
          \t\tgroup = "org.spring.boot"
          """
        );
    }

    @Test
    void shouldIncludeVersionRefInLibrariesSectionOfTomlVersionCatalog() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );

      JavaDependency dependency = javaDependency()
        .groupId("org.spring.boot")
        .artifactId("spring-boot-starter-web")
        .versionSlug("spring-boot")
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependency));

      assertThat(versionCatalogContent(emptyGradleProjectFolder)).contains(
          """
          [libraries.spring-boot-starter-web.version]
          \t\t\tref = "spring-boot"
          """
        );
    }

    @Test
    void shouldUseValidLibraryAlias() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );

      JavaDependency dependency = javaDependency().groupId("com.zaxxer").artifactId("HikariCP").build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependency));

      assertThat(versionCatalogContent(emptyGradleProjectFolder)).contains(
          """
          [libraries.hikariCP]
          \t\tname = "HikariCP"
          \t\tgroup = "com.zaxxer"
          """
        );
      assertThat(buildGradleContent(emptyGradleProjectFolder)).contains("implementation(libs.hikariCP)");
    }

    @EnumSource(value = JavaDependencyScope.class, mode = EXCLUDE, names = { "TEST", "RUNTIME", "PROVIDED", "IMPORT" })
    @ParameterizedTest
    void shouldAddImplementationDependencyInBuildGradleFileForScope(JavaDependencyScope scope) {
      JavaDependency dependency = javaDependency().groupId("org.spring.boot").artifactId("spring-boot-starter-web").scope(scope).build();
      new GradleCommandHandler(Indentation.DEFAULT, emptyGradleProjectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddDirectJavaDependency(dependency)
      );

      assertThat(buildGradleContent(emptyGradleProjectFolder)).contains("implementation(libs.spring.boot.starter.web)");
    }

    @Test
    void shouldAddRuntimeOnlyDependencyInBuildGradleFileForRuntimeScope() {
      JavaDependency dependency = javaDependency()
        .groupId("org.spring.boot")
        .artifactId("spring-boot-starter-web")
        .scope(JavaDependencyScope.RUNTIME)
        .build();
      new GradleCommandHandler(Indentation.DEFAULT, emptyGradleProjectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddDirectJavaDependency(dependency)
      );

      assertThat(buildGradleContent(emptyGradleProjectFolder)).contains("runtimeOnly(libs.spring.boot.starter.web)");
    }

    @Test
    void shouldAddCompileOnlyDependencyInBuildGradleFileForProvidedScope() {
      JavaDependency dependency = javaDependency()
        .groupId("org.spring.boot")
        .artifactId("spring-boot-starter-web")
        .scope(JavaDependencyScope.PROVIDED)
        .build();
      new GradleCommandHandler(Indentation.DEFAULT, emptyGradleProjectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddDirectJavaDependency(dependency)
      );

      assertThat(buildGradleContent(emptyGradleProjectFolder)).contains("compileOnly(libs.spring.boot.starter.web)");
    }

    @Test
    void shouldAddTestImplementationDependencyInBuildGradleFileForTestScope() {
      JavaDependency dependency = javaDependency()
        .groupId("org.junit.jupiter")
        .artifactId("junit-jupiter-engine")
        .scope(JavaDependencyScope.TEST)
        .build();
      new GradleCommandHandler(Indentation.DEFAULT, emptyGradleProjectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddDirectJavaDependency(dependency)
      );

      assertThat(buildGradleContent(emptyGradleProjectFolder)).contains("testImplementation(libs.junit.jupiter.engine)");
    }

    @Test
    void shouldAddDependencyExclusionsInBuildGradleFile() {
      JavaDependency dependency = javaDependency()
        .groupId("org.springframework.boot")
        .artifactId("spring-boot-starter-web")
        .addExclusion(groupId("org.springframework.boot"), artifactId("spring-boot-starter-tomcat"))
        .addExclusion(groupId("org.springframework.boot"), artifactId("spring-boot-starter-json"))
        .build();
      new GradleCommandHandler(Indentation.DEFAULT, emptyGradleProjectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddDirectJavaDependency(dependency)
      );

      assertThat(buildGradleContent(emptyGradleProjectFolder)).contains(
          """
            implementation(libs.spring.boot.starter.web) {
              exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
              exclude(group = "org.springframework.boot", module = "spring-boot-starter-json")
            }
          """
        );
    }

    @Test
    void shouldAddDependenciesInOrder() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );

      JavaDependency dependencyCompile = javaDependency()
        .groupId("org.junit.jupiter")
        .artifactId("junit-jupiter-engine")
        .scope(JavaDependencyScope.PROVIDED)
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependencyCompile));

      JavaDependency dependencyRuntime = javaDependency()
        .groupId("org.springframework.boot")
        .artifactId("spring-boot-starter-web")
        .scope(JavaDependencyScope.RUNTIME)
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependencyRuntime));

      JavaDependency dependencyTestImplementation = javaDependency()
        .groupId("org.junit.jupiter")
        .artifactId("junit-jupiter-engine")
        .scope(JavaDependencyScope.TEST)
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependencyTestImplementation));

      JavaDependency dependencyImplementation = javaDependency().groupId("com.google.guava").artifactId("guava").build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependencyImplementation));

      assertThat(buildGradleContent(emptyGradleProjectFolder)).contains(
          """
          dependencies {
            implementation(libs.guava)
            // jhipster-needle-gradle-implementation-dependencies
            compileOnly(libs.junit.jupiter.engine)
            // jhipster-needle-gradle-compile-dependencies
            runtimeOnly(libs.spring.boot.starter.web)
            // jhipster-needle-gradle-runtime-dependencies
            testImplementation(libs.junit.jupiter.engine)
            // jhipster-needle-gradle-test-dependencies
          }
          """
        );
    }

    @Test
    void shouldAddDependencyToBuildGradleProfileFile() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile");
      JavaDependency dependency = javaDependency()
        .groupId("org.spring.boot")
        .artifactId("spring-boot-starter-web")
        .scope(JavaDependencyScope.RUNTIME)
        .build();

      new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddDirectJavaDependency(dependency, localBuildProfile())
      );

      assertThat(scriptPluginContent(projectFolder, localBuildProfile())).contains(
          """
          runtimeOnly(libs.findLibrary("spring.boot.starter.web").get())\
          """
        );
    }
  }

  @Nested
  class HandleRemoveDirectJavaDependency {

    private final JHipsterProjectFolder emptyGradleProjectFolder = projectFrom("src/test/resources/projects/empty-gradle");

    @Test
    void shouldRemoveEntryInLibrariesSection() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new AddDirectJavaDependency(defaultVersionDependency()));

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(defaultVersionDependency().id()));

      assertThat(versionCatalogContent(emptyGradleProjectFolder))
        .doesNotContain("[libraries.spring-boot-starter]")
        .doesNotContain(
          """
          \t\tname = "spring-boot-starter"
          \t\tgroup = "org.springframework.boot"
          """
        );
    }

    @Test
    void shouldRemoveEntryInLibrariesSectionAndEntryInVersionsSection() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new SetVersion(jsonWebTokenVersion()));
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependencyWithVersion()));

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(dependencyWithVersion().id()));

      assertThat(versionCatalogContent(emptyGradleProjectFolder))
        .doesNotContain("json-web-token = ")
        .doesNotContain("[libraries.jjwt-jackson]")
        .doesNotContain(
          """
          \t\tname = "jjwt-jackson"
          \t\tgroup = "io.jsonwebtoken"\
          """
        )
        .doesNotContain("[libraries.jjwt-jackson.version]")
        .doesNotContain(
          """
          \t\t\tref = "json-web-token"
          """
        );
    }

    @Test
    void shouldRemoveEntryInLibrariesSectionButKeepEntryInVersionsSectionIfStillUsedByAnotherDependency() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new SetVersion(springBootVersion()));
      JavaDependency starterWebDependency = javaDependency()
        .groupId("org.springframework.boot")
        .artifactId("spring-boot-starter-web")
        .versionSlug("spring-boot")
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(starterWebDependency));
      gradleCommandHandler.handle(
        new AddDirectJavaDependency(
          javaDependency().groupId("org.springframework.boot").artifactId("spring-boot-starter-data-jpa").versionSlug("spring-boot").build()
        )
      );

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(starterWebDependency.id()));

      assertThat(versionCatalogContent(emptyGradleProjectFolder))
        .contains("spring-boot = ")
        .doesNotContain("[libraries.spring-boot-starter-web]")
        .contains("[libraries.spring-boot-starter-data-jpa]");
    }

    @Test
    void shouldRemoveDependencyInBuildGradleFile() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new AddDirectJavaDependency(defaultVersionDependency()));

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(defaultVersionDependency().id()));

      assertThat(buildGradleContent(emptyGradleProjectFolder)).doesNotContain("implementation(libs.spring.boot.starter)");
    }

    @Test
    void shouldRemoveDependencyWithExclusionInBuildGradleFile() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependencyWithVersionAndExclusion()));

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(dependencyWithVersionAndExclusion().id()));

      assertThat(buildGradleContent(emptyGradleProjectFolder)).doesNotContain("implementation(libs.jjwt.jackson)");
    }

    @Test
    void shouldRemoveTestDependencyInBuildGradleFile() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      JavaDependency dependency = javaDependency()
        .groupId("org.junit.jupiter")
        .artifactId("junit-jupiter-engine")
        .scope(JavaDependencyScope.TEST)
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependency));

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(dependency.id()));

      assertThat(buildGradleContent(emptyGradleProjectFolder)).doesNotContain("testImplementation(libs.junit.jupiter.engine)");
    }

    @Test
    void shouldRemoveRuntimeDependencyInBuildGradleFile() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      JavaDependency dependency = javaDependency()
        .groupId("org.junit.jupiter")
        .artifactId("junit-jupiter-engine")
        .scope(JavaDependencyScope.RUNTIME)
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependency));

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(dependency.id()));

      assertThat(buildGradleContent(emptyGradleProjectFolder)).doesNotContain("runtimeOnly(libs.junit.jupiter.engine)");
    }

    @Test
    void shouldRemoveProvidedDependencyInBuildGradleFile() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      JavaDependency dependency = javaDependency()
        .groupId("org.junit.jupiter")
        .artifactId("junit-jupiter-engine")
        .scope(JavaDependencyScope.PROVIDED)
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependency));

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(dependency.id()));

      assertThat(buildGradleContent(emptyGradleProjectFolder)).doesNotContain("compileOnly(libs.junit.jupiter.engine)");
    }

    @Test
    void shouldRemoveRuntimeDependencyInBuildGradleProfileFile() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile");
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        projectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      JavaDependency dependency = javaDependency()
        .groupId("org.junit.jupiter")
        .artifactId("junit-jupiter-engine")
        .scope(JavaDependencyScope.RUNTIME)
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependency, localBuildProfile()));

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(dependency.id(), localBuildProfile()));

      assertThat(scriptPluginContent(projectFolder, localBuildProfile())).doesNotContain(
          """
          runtimeOnly(libs.findLibrary("junit.jupiter.engine").get())
          """
        );
    }

    @Test
    void shouldNotRemoveEntryInLibrariesSectionWhenDependencyNotFoundInBuildGradleProfileFile() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile");
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        projectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      JavaDependency dependency = javaDependency()
        .groupId("org.junit.jupiter")
        .artifactId("junit-jupiter-engine")
        .scope(JavaDependencyScope.RUNTIME)
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependency));

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(dependency.id(), localBuildProfile()));

      assertThat(versionCatalogContent(projectFolder))
        .contains("[libraries.junit-jupiter-engine]")
        .contains(
          """
          \t\tname = "junit-jupiter-engine"
          \t\tgroup = "org.junit.jupiter"
          """
        );
    }

    @Test
    void shouldRemoveEntryInLibrariesSectionAndEntryInVersionsSectionWhenRemovedDependencyIsInBuildGradleProfileFile() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile");
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        projectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new SetVersion(jsonWebTokenVersion()));
      gradleCommandHandler.handle(new AddDirectJavaDependency(dependencyWithVersion(), localBuildProfile()));

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(dependencyWithVersion().id(), localBuildProfile()));

      assertThat(versionCatalogContent(projectFolder))
        .doesNotContain("json-web-token = ")
        .doesNotContain("[libraries.jjwt-jackson]")
        .doesNotContain(
          """
          \t\tname = "jjwt-jackson"
          \t\tgroup = "io.jsonwebtoken"\
          """
        )
        .doesNotContain("[libraries.jjwt-jackson.version]")
        .doesNotContain(
          """
          \t\t\tref = "json-web-token"
          """
        );
    }

    @Test
    void shouldRemoveEntryInLibrariesSectionButKeepEntryInVersionsSectionIfStillUsedByDependencyInBuildGradleProfileFile() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile");
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        projectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new SetVersion(springBootVersion()));
      JavaDependency starterWebDependency = javaDependency()
        .groupId("org.springframework.boot")
        .artifactId("spring-boot-starter-web")
        .versionSlug("spring-boot")
        .build();
      gradleCommandHandler.handle(new AddDirectJavaDependency(starterWebDependency));
      gradleCommandHandler.handle(
        new AddDirectJavaDependency(
          javaDependency()
            .groupId("org.springframework.boot")
            .artifactId("spring-boot-starter-data-jpa")
            .versionSlug("spring-boot")
            .build(),
          localBuildProfile()
        )
      );

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(starterWebDependency.id()));

      assertThat(versionCatalogContent(projectFolder))
        .contains("spring-boot = ")
        .doesNotContain("[libraries.spring-boot-starter-web]")
        .contains("[libraries.spring-boot-starter-data-jpa]");
    }
  }

  @Nested
  class HandleAddJavaDependencyManagement {

    private final JHipsterProjectFolder emptyGradleProjectFolder = projectFrom("src/test/resources/projects/empty-gradle");

    @Test
    void shouldAddEntryInLibrariesSectionToExistingTomlVersionCatalog() {
      new GradleCommandHandler(Indentation.DEFAULT, emptyGradleProjectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddJavaDependencyManagement(springBootDependencyManagement())
      );

      assertThat(versionCatalogContent(emptyGradleProjectFolder)).contains(
          """
          [libraries.spring-boot-dependencies]
          \t\tname = "spring-boot-dependencies"
          \t\tgroup = "org.springframework.boot"
          """
        );

      assertThat(versionCatalogContent(emptyGradleProjectFolder)).contains(
          """
          [libraries.spring-boot-dependencies.version]
          \t\t\tref = "spring-boot"
          """
        );
    }

    @Test
    void shouldAddImplementationDependencyInBuildGradleFileForScope() {
      new GradleCommandHandler(Indentation.DEFAULT, emptyGradleProjectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddJavaDependencyManagement(springBootDependencyManagement())
      );

      assertThat(buildGradleContent(emptyGradleProjectFolder)).contains("implementation(platform(libs.spring.boot.dependencies))");
    }

    @Test
    void shouldAddDependencyExclusionsInBuildGradleFile() {
      JavaDependency dependency = javaDependency()
        .groupId("org.springframework.boot")
        .artifactId("spring-boot-starter-web")
        .scope(JavaDependencyScope.IMPORT)
        .addExclusion(groupId("org.springframework.boot"), artifactId("spring-boot-starter-tomcat"))
        .addExclusion(groupId("org.springframework.boot"), artifactId("spring-boot-starter-json"))
        .build();
      new GradleCommandHandler(Indentation.DEFAULT, emptyGradleProjectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddJavaDependencyManagement(dependency)
      );

      assertThat(buildGradleContent(emptyGradleProjectFolder)).contains(
          """
            implementation(platform(libs.spring.boot.starter.web)) {
              exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
              exclude(group = "org.springframework.boot", module = "spring-boot-starter-json")
            }
          """
        );
    }

    @Test
    void shouldAddImplementationDependencyInBuildGradleProfileFile() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile");

      new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddJavaDependencyManagement(springBootDependencyManagement(), localBuildProfile())
      );

      assertThat(scriptPluginContent(projectFolder, localBuildProfile())).contains(
          """
          implementation(platform(libs.findLibrary("spring.boot.dependencies").get()))\
          """
        );
    }
  }

  @Nested
  class HandleRemoveJavaDependencyManagement {

    private final JHipsterProjectFolder emptyGradleProjectFolder = projectFrom("src/test/resources/projects/empty-gradle");

    @Test
    void shouldRemoveEntryInLibrariesSection() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new AddJavaDependencyManagement(springBootDependencyManagement()));

      gradleCommandHandler.handle(new RemoveJavaDependencyManagement(springBootDependencyManagement().id()));

      assertThat(versionCatalogContent(emptyGradleProjectFolder))
        .doesNotContain("[libraries.spring-boot-dependencies]")
        .doesNotContain(
          """
          \t\tname = "spring-boot-dependencies"
          \t\tgroup = "org.springframework.boot"
          """
        );
    }

    @Test
    void shouldRemoveEntryInLibrariesSectionAndEntryInVersionsSection() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new SetVersion(springBootVersion()));
      gradleCommandHandler.handle(new AddJavaDependencyManagement(springBootDependencyManagement()));

      gradleCommandHandler.handle(new RemoveJavaDependencyManagement(springBootDependencyManagement().id()));

      assertThat(versionCatalogContent(emptyGradleProjectFolder))
        .doesNotContain("spring-boot = ")
        .doesNotContain("[libraries.spring-boot-dependencies]")
        .doesNotContain(
          """
          \t\tname = "spring-boot-dependencies"
          \t\tgroup = "org.springframework.boot"
          """
        );
    }

    @Test
    void shouldRemoveEntryInLibrariesSectionButKeepEntryInVersionsSectionIfStillUsedByAnotherDependencyManagement() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new SetVersion(springBootVersion()));
      gradleCommandHandler.handle(new AddJavaDependencyManagement(springBootDependencyManagement()));
      gradleCommandHandler.handle(
        new AddJavaDependencyManagement(
          javaDependency()
            .groupId("org.springframework.boot")
            .artifactId("spring-boot-starter-data-jpa")
            .versionSlug("spring-boot")
            .scope(JavaDependencyScope.IMPORT)
            .build()
        )
      );

      gradleCommandHandler.handle(new RemoveJavaDependencyManagement(springBootDependencyManagement().id()));

      assertThat(versionCatalogContent(emptyGradleProjectFolder))
        .contains("spring-boot = ")
        .doesNotContain("[libraries.spring-boot-dependencies]")
        .contains("[libraries.spring-boot-starter-data-jpa]");
    }

    @Test
    void shouldRemoveDependencyInBuildGradleFile() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new AddJavaDependencyManagement(springBootDependencyManagement()));

      gradleCommandHandler.handle(new RemoveJavaDependencyManagement(springBootDependencyManagement().id()));

      assertThat(buildGradleContent(emptyGradleProjectFolder)).doesNotContain("implementation(platform(libs.spring.boot.dependencies))");
    }

    @Test
    void shouldRemoveDependencyWithExclusionInBuildGradleFile() {
      var dependency = javaDependency()
        .groupId("org.springframework.boot")
        .artifactId("spring-boot-dependencies")
        .addExclusion(jsonWebTokenDependencyId())
        .addExclusion(springBootDependencyId())
        .scope(JavaDependencyScope.IMPORT)
        .type(JavaDependencyType.POM)
        .build();
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new AddJavaDependencyManagement(dependency));
      assertThat(buildGradleContent(emptyGradleProjectFolder)).contains("implementation(platform(libs.spring.boot.dependencies))");

      gradleCommandHandler.handle(new RemoveDirectJavaDependency(dependency.id()));

      assertThat(buildGradleContent(emptyGradleProjectFolder)).doesNotContain("implementation(platform(libs.spring.boot.dependencies))");
    }

    @Test
    void shouldRemoveDependencyInBuildGradleProfileFile() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile");
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        projectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new AddJavaDependencyManagement(springBootDependencyManagement(), localBuildProfile()));

      gradleCommandHandler.handle(new RemoveJavaDependencyManagement(springBootDependencyManagement().id(), localBuildProfile()));

      assertThat(buildGradleContent(projectFolder)).doesNotContain(
          """
          implementation(platform(libs.findLibrary("spring.boot.dependencies").get()))\
          """
        );
    }

    @Test
    void shouldNotRemoveEntryInLibrariesSectionWhenDependencyManagerNotFoundInBuildGradleProfileFile() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile");
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        projectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new AddJavaDependencyManagement(springBootDependencyManagement()));

      gradleCommandHandler.handle(new RemoveJavaDependencyManagement(springBootDependencyManagement().id(), localBuildProfile()));

      assertThat(versionCatalogContent(projectFolder))
        .contains("[libraries.spring-boot-dependencies]")
        .contains(
          """
          \t\tname = "spring-boot-dependencies"
          \t\tgroup = "org.springframework.boot"
          """
        );
    }

    @Test
    void shouldRemoveEntryInLibrariesSectionAndEntryInVersionsSectionWhenRemovedDependencyManagementIsInBuildGradleProfileFile() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile");
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        projectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new SetVersion(springBootVersion()));
      gradleCommandHandler.handle(new AddJavaDependencyManagement(springBootDependencyManagement(), localBuildProfile()));

      gradleCommandHandler.handle(new RemoveJavaDependencyManagement(springBootDependencyManagement().id(), localBuildProfile()));

      assertThat(versionCatalogContent(projectFolder))
        .doesNotContain("spring-boot = ")
        .doesNotContain("[libraries.spring-boot-dependencies]")
        .doesNotContain(
          """
          \t\tname = "spring-boot-dependencies"
          \t\tgroup = "org.springframework.boot"
          """
        );
    }

    @Test
    void shouldRemoveEntryInLibrariesSectionButKeepEntryInVersionsSectionIfStillUsedByDependencyManagementInBuildGradleProfileFile() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile");
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        projectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      gradleCommandHandler.handle(new SetVersion(springBootVersion()));
      gradleCommandHandler.handle(new AddJavaDependencyManagement(springBootDependencyManagement()));
      gradleCommandHandler.handle(
        new AddJavaDependencyManagement(
          javaDependency()
            .groupId("org.springframework.boot")
            .artifactId("spring-boot-starter-data-jpa")
            .versionSlug("spring-boot")
            .scope(JavaDependencyScope.IMPORT)
            .build(),
          localBuildProfile()
        )
      );

      gradleCommandHandler.handle(new RemoveJavaDependencyManagement(springBootDependencyManagement().id()));

      assertThat(versionCatalogContent(projectFolder))
        .contains("spring-boot = ")
        .doesNotContain("[libraries.spring-boot-dependencies]")
        .contains("[libraries.spring-boot-starter-data-jpa]");
    }
  }

  @Nested
  class HandleAddGradlePlugin {

    private final JHipsterProjectFolder emptyGradleProjectFolder = projectFrom("src/test/resources/projects/empty-gradle");

    @Test
    void shouldDeclareAndConfigureCorePluginAndAddImportInBuildGradleFile() {
      new GradleCommandHandler(Indentation.DEFAULT, emptyGradleProjectFolder, emptyModuleContext(), files, fileReplacer).handle(
        AddGradlePlugin.builder().plugin(checkstyleGradlePlugin()).build()
      );

      assertThat(buildGradleContent(emptyGradleProjectFolder))
        .contains(
          """
          import java.util.Properties
          // jhipster-needle-gradle-imports
          """
        )
        .contains(
          """
          plugins {
            java
            checkstyle
            // jhipster-needle-gradle-plugins
          }
          """
        )
        .contains(
          """

          checkstyle {
            toolVersion = libs.versions.checkstyle.get()
          }

          // jhipster-needle-gradle-plugins-configurations
          """
        );
    }

    @Test
    void shouldDeclareAndConfigureCommunityPluginAndAddImportInBuildGradleFile() {
      new GradleCommandHandler(Indentation.DEFAULT, emptyGradleProjectFolder, emptyModuleContext(), files, fileReplacer).handle(
        AddGradlePlugin.builder().plugin(nodeGradlePlugin()).build()
      );

      assertThat(buildGradleContent(emptyGradleProjectFolder))
        .contains(
          """
          import com.github.gradle.node.npm.task.NpmTask
          // jhipster-needle-gradle-imports
          """
        )
        .contains(
          """
          plugins {
            java
            alias(libs.plugins.node.gradle)
            // jhipster-needle-gradle-plugins
          }
          """
        )
        .contains(
          """
          node {
            version.set("v20.14.0")
            npmVersion.set("10.8.1")
            npmWorkDir.set(file("build"))
          }

          val buildTaskUsingNpm = tasks.register<NpmTask>("buildNpm") {
            description = "Build the frontend project using NPM"
            group = "Build"
            dependsOn("npmInstall")
            npmCommand.set(listOf("run", "build"))
            environment.set(mapOf("APP_VERSION" to project.version.toString()))
          }

          val testTaskUsingNpm = tasks.register<NpmTask>("testNpm") {
            description = "Test the frontend project using NPM"
            group = "verification"
            dependsOn("npmInstall", "buildNpm")
            npmCommand.set(listOf("run", "test"))
            ignoreExitValue.set(false)
            workingDir.set(projectDir)
            execOverrides {
              standardOutput = System.out
            }
          }

          tasks.bootJar {
            dependsOn("buildNpm")
            from("build/classes/static") {
                into("BOOT-INF/classes/static")
            }
          }
          """
        );
    }

    @Test
    void shouldApplyVersionCatalogReferenceConvention() {
      GradlePlugin plugin = gradleCommunityPlugin().id("org.springframework.boot").pluginSlug("spring-boot").build();
      AddGradlePlugin build = AddGradlePlugin.builder().plugin(plugin).build();
      new GradleCommandHandler(Indentation.DEFAULT, emptyGradleProjectFolder, emptyModuleContext(), files, fileReplacer).handle(build);

      assertThat(versionCatalogContent(emptyGradleProjectFolder)).contains("[plugins.spring-boot]");
      // "-" is transformed to "." in the plugin slug
      assertThat(buildGradleContent(emptyGradleProjectFolder)).contains("alias(libs.plugins.spring.boot)");
    }

    @Test
    void shouldAddToolVersion() {
      AddGradlePlugin build = AddGradlePlugin.builder().plugin(checkstyleGradlePlugin()).toolVersion(checkstyleToolVersion()).build();
      new GradleCommandHandler(Indentation.DEFAULT, emptyGradleProjectFolder, emptyModuleContext(), files, fileReplacer).handle(build);

      assertThat(versionCatalogContent(emptyGradleProjectFolder)).contains(
          """
          checkstyle = "8.42.1"
          """
        );
    }

    @Test
    void shouldAddPluginVersion() {
      AddGradlePlugin build = AddGradlePlugin.builder().plugin(checkstyleGradlePlugin()).pluginVersion(checkstyleToolVersion()).build();
      new GradleCommandHandler(Indentation.DEFAULT, emptyGradleProjectFolder, emptyModuleContext(), files, fileReplacer).handle(build);

      assertThat(versionCatalogContent(emptyGradleProjectFolder)).contains(
          """
          checkstyle = "8.42.1"
          """
        );
    }

    @Test
    void shouldIgnoreAlreadyDeclaredPluginInBuildGradleFile() {
      GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
        Indentation.DEFAULT,
        emptyGradleProjectFolder,
        emptyModuleContext(),
        files,
        fileReplacer
      );
      AddGradlePlugin command = AddGradlePlugin.builder().plugin(checkstyleGradlePlugin()).build();
      gradleCommandHandler.handle(command);

      gradleCommandHandler.handle(command);

      assertThat(buildGradleContent(emptyGradleProjectFolder))
        .containsOnlyOnce(
          """
          import java.util.Properties
          """
        )
        .contains(
          """
          plugins {
            java
            checkstyle
            // jhipster-needle-gradle-plugins
          }
          """
        )
        .contains(
          """
          java {
            toolchain {
              languageVersion = JavaLanguageVersion.of(21)
            }
          }

          checkstyle {
            toolVersion = libs.versions.checkstyle.get()
          }

          // jhipster-needle-gradle-plugins-configurations
          """
        );
    }

    @Test
    void shouldDeclareAndConfigureCommunityPluginInBuildGradleProfileFile() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile");

      new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
        AddGradlePlugin.builder().plugin(gitPropertiesGradleProfilePlugin()).buildProfile(localBuildProfile()).build()
      );

      assertThat(versionCatalogContent(projectFolder)).contains(
          """
          [libraries.gradle-git-properties]
          \t\tname = "gradle-git-properties"
          \t\tgroup = "com.gorylenko.gradle-git-properties"
          """
        );
      assertThat(versionCatalogContent(projectFolder)).contains(
          """
          [libraries.gradle-git-properties.version]
          \t\t\tref = "git-properties"
          """
        );
      assertThat(pluginBuildGradleContent(projectFolder)).contains("implementation(libs.gradle.git.properties)");
      assertThat(scriptPluginContent(projectFolder, localBuildProfile()))
        .contains(
          """
          plugins {
            java
            id("com.gorylenko.gradle-git-properties")
            // jhipster-needle-gradle-plugins
          }
          """
        )
        .contains(
          """

          gitProperties {
            failOnNoGitDirectory = false
            keys = listOf("git.branch", "git.commit.id.abbrev", "git.commit.id.describe", "git.build.version")
          }

          // jhipster-needle-gradle-plugins-configurations
          """
        );
    }

    @Test
    void shouldDeclareCommunityPluginWithDifferentGroupIdAndPluginIdInBuildGradleProfileFile() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile");

      new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
        AddGradlePlugin.builder().plugin(dockerGradlePluginDependency()).buildProfile(localBuildProfile()).build()
      );

      assertThat(versionCatalogContent(projectFolder)).contains(
          """
          [libraries.gradle-docker-plugin]
          \t\tname = "gradle-docker-plugin"
          \t\tgroup = "com.bmuschko"
          """
        );
      assertThat(pluginBuildGradleContent(projectFolder)).contains("implementation(libs.gradle.docker.plugin");
      assertThat(scriptPluginContent(projectFolder, localBuildProfile()))
        .contains(
          """
          import java.util.Properties
          // jhipster-needle-gradle-imports
          """
        )
        .contains(
          """
          plugins {
            java
            id("com.bmuschko.docker-remote-api")
            // jhipster-needle-gradle-plugins
          }
          """
        );
    }

    @Test
    void shouldDeclareAndConfigureCorePluginAndAddImportInBuildGradleProfileFile() {
      JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/gradle-with-local-profile");

      new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
        AddGradlePlugin.builder().plugin(checkstyleGradleProfilePlugin()).buildProfile(localBuildProfile()).build()
      );

      assertThat(scriptPluginContent(projectFolder, localBuildProfile()))
        .contains(
          """
          import java.util.Properties
          // jhipster-needle-gradle-imports
          """
        )
        .contains(
          """
          plugins {
            java
            checkstyle
            // jhipster-needle-gradle-plugins
          }
          """
        )
        .contains(
          """
          checkstyle {
            toolVersion = libs.versions.checkstyle.get()
          }
          """
        );
    }
  }

  @Test
  void addMavenBuildExtensionShouldNotBeHandled() {
    JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty");

    GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
      Indentation.DEFAULT,
      projectFolder,
      emptyModuleContext(),
      files,
      fileReplacer
    );
    AddMavenBuildExtension command = new AddMavenBuildExtension(mavenBuildExtensionWithSlug());
    assertThatCode(() -> gradleCommandHandler.handle(command)).doesNotThrowAnyException();
  }

  @Test
  void addAddDirectMavenPluginShouldNotBeHandled() {
    JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty");

    GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
      Indentation.DEFAULT,
      projectFolder,
      emptyModuleContext(),
      files,
      fileReplacer
    );
    AddDirectMavenPlugin command = AddDirectMavenPlugin.builder().plugin(mavenEnforcerPlugin()).build();
    assertThatCode(() -> gradleCommandHandler.handle(command)).doesNotThrowAnyException();
  }

  @Test
  void addAddBuildPluginManagementShouldNotBeHandled() {
    JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty");

    GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
      Indentation.DEFAULT,
      projectFolder,
      emptyModuleContext(),
      files,
      fileReplacer
    );
    AddMavenPluginManagement command = AddMavenPluginManagement.builder().plugin(mavenEnforcerPlugin()).build();
    assertThatCode(() -> gradleCommandHandler.handle(command)).doesNotThrowAnyException();
  }

  @Test
  void shouldAddGradleFreeConfigurationBlock() {
    JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");

    new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddGradleConfiguration(
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
          """
        )
      );

    assertThat(buildGradleContent(projectFolder)).contains(
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

        // jhipster-needle-gradle-free-configuration-blocks\
        """
      );
  }

  @Test
  void shouldNotDuplicateExistingGradleFreeConfigurationBlock() {
    JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");
    GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
      Indentation.DEFAULT,
      projectFolder,
      emptyModuleContext(),
      files,
      fileReplacer
    );
    gradleCommandHandler.handle(
      new AddGradleConfiguration(
        """
        tasks.build {
          dependsOn("processResources")
        }\
        """
      )
    );

    gradleCommandHandler.handle(
      new AddGradleConfiguration(
        """
        tasks.build {
          dependsOn("processResources")
        }\
        """
      )
    );

    assertThat(buildGradleContent(projectFolder)).containsOnlyOnce(
        """
        tasks.build {
          dependsOn("processResources")
        }\
        """
      );
  }

  @Test
  void shouldAddTasksTestInstruction() {
    JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");

    new GradleCommandHandler(Indentation.DEFAULT, projectFolder, emptyModuleContext(), files, fileReplacer).handle(
        new AddGradleTasksTestInstruction(
          """
          dependsOn("testNpm")\
          """
        )
      );

    assertThat(buildGradleContent(projectFolder)).contains(
        """
        tasks.test {
          filter {
            includeTestsMatching("**Test*")
            excludeTestsMatching("**IT*")
            excludeTestsMatching("**CucumberTest*")
          }
          useJUnitPlatform()
          dependsOn("testNpm")
          // jhipster-needle-gradle-tasks-test
        }
        """
      );
  }

  @Test
  void shouldNotDuplicateExistingTasksTestInstruction() {
    JHipsterProjectFolder projectFolder = projectFrom("src/test/resources/projects/empty-gradle");
    GradleCommandHandler gradleCommandHandler = new GradleCommandHandler(
      Indentation.DEFAULT,
      projectFolder,
      emptyModuleContext(),
      files,
      fileReplacer
    );
    gradleCommandHandler.handle(
      new AddGradleTasksTestInstruction(
        """
        dependsOn("testNpm")\
        """
      )
    );

    gradleCommandHandler.handle(
      new AddGradleTasksTestInstruction(
        """
        dependsOn("testNpm")\
        """
      )
    );

    assertThat(buildGradleContent(projectFolder)).contains(
        """
        tasks.test {
          filter {
            includeTestsMatching("**Test*")
            excludeTestsMatching("**IT*")
            excludeTestsMatching("**CucumberTest*")
          }
          useJUnitPlatform()
          dependsOn("testNpm")
          // jhipster-needle-gradle-tasks-test
        }
        """
      );
  }

  private static String buildGradleContent(JHipsterProjectFolder projectFolder) {
    return content(Path.of(projectFolder.get()).resolve("build.gradle.kts"));
  }

  private static String versionCatalogContent(JHipsterProjectFolder projectFolder) {
    return contentNormalizingNewLines(Path.of(projectFolder.get()).resolve("gradle/libs.versions.toml"));
  }

  private static String pluginBuildGradleContent(JHipsterProjectFolder projectFolder) {
    return content(Path.of(projectFolder.get()).resolve("buildSrc/build.gradle.kts"));
  }

  private static String scriptPluginContent(JHipsterProjectFolder projectFolder, BuildProfileId buildProfileId) {
    return content(Path.of(projectFolder.get()).resolve("buildSrc/src/main/kotlin/profile-%s.gradle.kts".formatted(buildProfileId)));
  }

  private static void assertFileExists(JHipsterProjectFolder projectFolder, String other, String description) {
    Path profileGradlePath = Path.of(projectFolder.get()).resolve(other);
    assertThat(Files.exists(profileGradlePath)).as(description, profileGradlePath.toString()).isTrue();
  }
}
