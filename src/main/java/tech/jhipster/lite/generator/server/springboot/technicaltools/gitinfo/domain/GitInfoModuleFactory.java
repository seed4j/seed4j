package tech.jhipster.lite.generator.server.springboot.technicaltools.gitinfo.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class GitInfoModuleFactory {

  private static final String PACKAGE_INFO = "package-info.java";

  private static final JHipsterSource SOURCE = from("server/springboot/technicaltools/gitinfo");
  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final String PRIMARY = "/infrastructure/primary";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath).append("wire/gitinfo");

    //@formatter:off
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
