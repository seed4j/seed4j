package com.seed4j.generator.server.javatool.checkstyle.domain;

import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.gradleCorePlugin;
import static com.seed4j.module.domain.SeedModule.javaDependency;
import static com.seed4j.module.domain.SeedModule.mavenPlugin;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.pluginExecution;
import static com.seed4j.module.domain.SeedModule.to;
import static com.seed4j.module.domain.mavenplugin.MavenBuildPhase.VALIDATE;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.gradleplugin.GradleMainBuildPlugin;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class CheckstyleModuleFactory {

  private static final SeedSource TEMPLATES_SOURCE = from("server/javatool/checkstyle/main");
  private static final String CHECKSTYLE = "checkstyle";

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .mavenPlugins()
        .plugin(checkstyleMavenPlugin())
        .and()
      .gradlePlugins()
        .plugin(checkstyleGradlePlugin())
        .and()
      .files()
        .add(TEMPLATES_SOURCE.template("checkstyle.xml"), to("checkstyle.xml"))
        .and()
      .build();
    // @formatter:on
  }

  private MavenPlugin checkstyleMavenPlugin() {
    return mavenPlugin()
      .groupId("org.apache.maven.plugins")
      .artifactId("maven-checkstyle-plugin")
      .versionSlug("maven-checkstyle-plugin")
      .configuration(
        """
        <configLocation>checkstyle.xml</configLocation>
        <consoleOutput>true</consoleOutput>
        <failsOnError>true</failsOnError>
        <includeTestSourceDirectory>true</includeTestSourceDirectory>
        <sourceDirectories>
          <!-- only include main source directory, not generated sources directories -->
          <sourceDirectory>${project.build.sourceDirectory}</sourceDirectory>
        </sourceDirectories>
        <testSourceDirectories>
          <!-- only include main test source directory, not generated test sources directories -->
          <testSourceDirectory>${project.build.testSourceDirectory}</testSourceDirectory>
        </testSourceDirectories>
        """
      )
      .addDependency(checkstyleDependency())
      .addExecution(pluginExecution().goals("check").id("validate").phase(VALIDATE))
      .build();
  }

  private JavaDependency checkstyleDependency() {
    return javaDependency().groupId("com.puppycrawl.tools").artifactId(CHECKSTYLE).versionSlug(CHECKSTYLE).build();
  }

  private GradleMainBuildPlugin checkstyleGradlePlugin() {
    VersionSlug toolVersionSlug = new VersionSlug(CHECKSTYLE);
    return gradleCorePlugin()
      .id(CHECKSTYLE)
      .toolVersionSlug(toolVersionSlug)
      .configuration(
        """
        checkstyle {
          configFile = rootProject.file("checkstyle.xml")
          toolVersion = libs.versions.%s.get()
        }
        """.formatted(toolVersionSlug.slug())
      )
      .build();
  }
}
