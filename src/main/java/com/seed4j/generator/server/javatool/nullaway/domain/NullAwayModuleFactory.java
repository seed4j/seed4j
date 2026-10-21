package com.seed4j.generator.server.javatool.nullaway.domain;

import static com.seed4j.module.domain.Seed4JModule.javaDependency;
import static com.seed4j.module.domain.Seed4JModule.mavenPlugin;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.pluginExecution;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class NullAwayModuleFactory {

  private static final String NULLABILITY_MAVEN_PLUGIN = "nullability-maven-plugin";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependencyManagement(errorProneCoreManagement())
        .and()
      .mavenPlugins()
        .pluginManagement(nullabilityMavenPluginManagement())
        .plugin(nullabilityMavenPlugin().build())
        .and()
      .build();
    // @formatter:on
  }

  private JavaDependency errorProneCoreManagement() {
    return javaDependency().groupId("com.google.errorprone").artifactId("error_prone_core").versionSlug("error-prone").build();
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
        <errorProneVersion>${error-prone.version}</errorProneVersion>
        <nullAwayVersion>${nullaway.version}</nullAwayVersion>
        <outputDirectory>${project.basedir}/src/main/java</outputDirectory>
        <testOutputDirectory>${project.basedir}/src/test/java</testOutputDirectory>
        """
      )
      .addExecution(pluginExecution().goals("configure"))
      .build();
  }
}
