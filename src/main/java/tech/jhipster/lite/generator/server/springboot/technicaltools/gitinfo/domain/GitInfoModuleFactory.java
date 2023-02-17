package tech.jhipster.lite.generator.server.springboot.technicaltools.gitinfo.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin;
import tech.jhipster.lite.module.domain.javaproperties.Comment;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class GitInfoModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/technicaltools/gitinfo");
  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final String GIT_INFO_PRIMARY = "gitinfo/infrastructure/primary";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .add(
          MAIN_SOURCE.template("GitInfoConfiguration.java"),
          toSrcMainJava().append(packagePath).append(GIT_INFO_PRIMARY).append("GitInfoConfiguration.java")
        )
        .and()
      .springMainProperties()
        .comment(propertyKey("management.info.git.mode"), new Comment("Git Information"))
        .set(propertyKey("management.info.git.mode"), propertyValue("full"))
        .set(propertyKey("management.info.git.enabled"), propertyValue("true"))
        .set(propertyKey("management.info.env.enabled"), propertyValue("true"))
        .and()
      .javaBuildPlugins()
        .plugin(gitCommitIdPlugin())
        .pluginManagement(gitCommitIdPluginManagement())
        .and()
      .build();
    //@formatter:on
  }

  private JavaBuildPlugin gitCommitIdPluginManagement() {
    return JavaBuildPlugin
      .builder()
      .groupId("io.github.git-commit-id")
      .artifactId("git-commit-id-maven-plugin")
      .versionSlug("git-commit-id-plugin")
      .additionalElements(
        """
        <executions>
          <execution>
            <goals>
              <goal>revision</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <failOnNoGitDirectory>false</failOnNoGitDirectory>
          <failOnUnableToExtractRepoInfo>false</failOnUnableToExtractRepoInfo>
          <generateGitPropertiesFile>true</generateGitPropertiesFile>
          <includeOnlyProperties>
            <includeOnlyProperty>^git.commit.id.abbrev$</includeOnlyProperty>
            <includeOnlyProperty>^git.commit.id.describe$</includeOnlyProperty>
            <includeOnlyProperty>^git.branch$</includeOnlyProperty>
          </includeOnlyProperties>
          <verbose>false</verbose>
        </configuration>
        """
      )
      .build();
  }

  private JavaBuildPlugin gitCommitIdPlugin() {
    return JavaBuildPlugin.builder().groupId("io.github.git-commit-id").artifactId("git-commit-id-maven-plugin").build();
  }
}
