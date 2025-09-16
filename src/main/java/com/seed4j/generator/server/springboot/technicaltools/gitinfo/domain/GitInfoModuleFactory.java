package com.seed4j.generator.server.springboot.technicaltools.gitinfo.domain;

import static com.seed4j.module.domain.Seed4JModule.comment;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.mavenPlugin;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.pluginExecution;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.gradleplugin.GradleCommunityPlugin;
import com.seed4j.module.domain.gradleplugin.GradleMainBuildPlugin;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class GitInfoModuleFactory {

  private static final String PACKAGE_INFO = "package-info.java";

  private static final Seed4JSource SOURCE = from("server/springboot/technicaltools/gitinfo");
  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final String PRIMARY = "/infrastructure/primary";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    Seed4JDestination mainDestination = toSrcMainJava().append(packagePath).append("wire/gitinfo");

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(
          MAIN_SOURCE.template("GitInfoConfiguration.java"),
          mainDestination.append(PRIMARY).append("GitInfoConfiguration.java")
        )
        .add(MAIN_SOURCE.template(PACKAGE_INFO), mainDestination.append(PACKAGE_INFO))
        .and()
      .springMainProperties()
        .comment(propertyKey("management.info.git"), comment("Git Information"))
        .set(propertyKey("management.info.git.mode"), propertyValue("full"))
        .set(propertyKey("management.info.git.enabled"), propertyValue(true))
        .set(propertyKey("management.info.env.enabled"), propertyValue(true))
        .and()
      .mavenPlugins()
        .plugin(gitCommitIdPlugin())
        .pluginManagement(gitCommitIdPluginManagement())
        .and()
      .gradlePlugins()
        .plugin(gradleGitPropertiesPlugin())
        .and()
      .build();
    // @formatter:on
  }

  private GradleMainBuildPlugin gradleGitPropertiesPlugin() {
    return GradleCommunityPlugin.builder()
      .id("com.gorylenko.gradle-git-properties")
      .pluginSlug("git-properties")
      .versionSlug("git-properties")
      .configuration(
        """
        gitProperties {
          failOnNoGitDirectory = false
          keys = listOf("git.branch", "git.commit.id.abbrev", "git.commit.id.describe", "git.build.version")
        }
        """
      )
      .build();
  }

  private MavenPlugin gitCommitIdPluginManagement() {
    return MavenPlugin.builder()
      .groupId("io.github.git-commit-id")
      .artifactId("git-commit-id-maven-plugin")
      .versionSlug("git-commit-id-plugin")
      .configuration(
        """
          <failOnNoGitDirectory>false</failOnNoGitDirectory>
          <failOnUnableToExtractRepoInfo>false</failOnUnableToExtractRepoInfo>
          <generateGitPropertiesFile>true</generateGitPropertiesFile>
          <includeOnlyProperties>
            <includeOnlyProperty>^git.commit.id.abbrev$</includeOnlyProperty>
            <includeOnlyProperty>^git.commit.id.describe$</includeOnlyProperty>
            <includeOnlyProperty>^git.branch$</includeOnlyProperty>
            <includeOnlyProperty>^git.build.(time|version)$</includeOnlyProperty>
          </includeOnlyProperties>
          <verbose>false</verbose>
        """
      )
      .addExecution(pluginExecution().goals("revision"))
      .build();
  }

  private MavenPlugin gitCommitIdPlugin() {
    return mavenPlugin().groupId("io.github.git-commit-id").artifactId("git-commit-id-maven-plugin").build();
  }
}
