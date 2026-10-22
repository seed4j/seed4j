package com.seed4j.generator.server.javatool.nullaway.domain;

import static com.seed4j.module.domain.Seed4JModule.gradleCommunityPlugin;
import static com.seed4j.module.domain.Seed4JModule.mavenPlugin;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.pluginExecution;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.gradleplugin.GradleMainBuildPlugin;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class NullAwayModuleFactory {

  private static final String NULLABILITY_MAVEN_PLUGIN = "nullability-maven-plugin";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .mavenPlugins()
        .pluginManagement(nullabilityMavenPluginManagement())
        .plugin(nullabilityMavenPlugin().build())
        .and()
      .gradlePlugins()
        .plugin(errorProneGradlePlugin())
        .plugin(nullAwayGradlePlugin())
        .and()
      .build();
    // @formatter:on
  }

  private MavenPlugin.MavenPluginOptionalBuilder nullabilityMavenPlugin() {
    return mavenPlugin().groupId("am.ik.maven").artifactId(NULLABILITY_MAVEN_PLUGIN);
  }

  private MavenPlugin nullabilityMavenPluginManagement() {
    return nullabilityMavenPlugin()
      .versionSlug(NULLABILITY_MAVEN_PLUGIN)
      .extensions()
      .configuration(
        """
        <nullAwayVersion>${nullaway.version}</nullAwayVersion>
        <outputDirectory>${project.basedir}/src/main/java</outputDirectory>
        <testOutputDirectory>${project.basedir}/src/test/java</testOutputDirectory>
        """
      )
      .addExecution(pluginExecution().goals("configure"))
      .build();
  }

  private GradleMainBuildPlugin errorProneGradlePlugin() {
    return gradleCommunityPlugin()
      .id("net.ltgt.errorprone")
      .pluginSlug("errorprone")
      .versionSlug("gradle-errorprone-plugin")
      .toolVersionSlug("error-prone")
      .withBuildGradleImport("net.ltgt.gradle.errorprone.errorprone")
      .configuration(
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
      )
      .build();
  }

  private GradleMainBuildPlugin nullAwayGradlePlugin() {
    return gradleCommunityPlugin()
      .id("net.ltgt.nullaway")
      .pluginSlug("nullaway")
      .versionSlug("gradle-nullaway-plugin")
      .toolVersionSlug("nullaway")
      .withBuildGradleImport("net.ltgt.gradle.nullaway.nullaway")
      .configuration(
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
      .build();
  }
}
