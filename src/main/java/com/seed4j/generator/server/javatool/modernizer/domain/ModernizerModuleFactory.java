package com.seed4j.generator.server.javatool.modernizer.domain;

import static com.seed4j.module.domain.Seed4JModule.gradleCommunityPlugin;
import static com.seed4j.module.domain.Seed4JModule.mavenPlugin;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.pluginExecution;
import static com.seed4j.module.domain.mavenplugin.MavenBuildPhase.VERIFY;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.gradleplugin.GradleMainBuildPlugin;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class ModernizerModuleFactory {

  private static final String MODERNIZER = "modernizer";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .mavenPlugins()
        .pluginManagement(modernizerMavenPluginManagement())
        .plugin(modernizerMavenPlugin().build())
        .and()
      .gradlePlugins()
        .plugin(modernizerGradlePlugin())
        .and()
      .build();
    // @formatter:on
  }

  private MavenPlugin.MavenPluginOptionalBuilder modernizerMavenPlugin() {
    return mavenPlugin().groupId("org.gaul").artifactId("modernizer-maven-plugin");
  }

  private MavenPlugin modernizerMavenPluginManagement() {
    return modernizerMavenPlugin()
      .versionSlug("modernizer-maven-plugin")
      .configuration(
        """
        <javaVersion>${java.version}</javaVersion>
        <failOnViolations>true</failOnViolations>
        """
      )
      .addExecution(pluginExecution().goals(MODERNIZER).id(MODERNIZER).phase(VERIFY))
      .build();
  }

  private GradleMainBuildPlugin modernizerGradlePlugin() {
    return gradleCommunityPlugin()
      .id("com.github.andygoossens.modernizer")
      .pluginSlug(MODERNIZER)
      .versionSlug(MODERNIZER)
      .configuration(
        """
        modernizer {
          failOnViolations = true
          includeTestClasses = true
        }
        """
      )
      .build();
  }
}
