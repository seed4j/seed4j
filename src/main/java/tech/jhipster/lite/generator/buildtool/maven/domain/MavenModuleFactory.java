package tech.jhipster.lite.generator.buildtool.maven.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.javadependency.Version;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPlugin;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class MavenModuleFactory {

  private static final JHipsterSource SOURCE = from("buildtool/maven");

  private static final GroupId APACHE_PLUGINS_GROUP = groupId("org.apache.maven.plugins");
  private static final ArtifactId ENFORCER_ARTIFACTID = artifactId("maven-enforcer-plugin");

  private static final String JAVA_PREREQUISITES =
    """

    ### Java

    You need to have Java 21:
    - [JDK 21](https://openjdk.java.net/projects/jdk/21/)""";

  public JHipsterModule buildMavenModule(JHipsterModuleProperties properties, Version mavenVersion) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .put("dasherizedBaseName", properties.projectBaseName().kebabCase())
        .put("latestMavenVersion", mavenVersion.version())
        .and()
      .prerequisites(JAVA_PREREQUISITES)
      .files()
        .add(SOURCE.template("pom.xml"), to("pom.xml"))
        .and()
      .javaDependencies()
        .addDependency(junitEngineDependency())
        .addDependency(junitParamsDependency())
        .addDependency(assertjDependency())
        .addDependency(mockitoDependency())
        .and()
      .mavenPlugins()
        .plugin(mavenCompilerPlugin())
        .plugin(surefirePlugin())
        .plugin(failsafePlugin())
        .plugin(enforcerPlugin())
        .pluginManagement(enforcerPluginManagement())
        .and()
      .build();
    //@formatter:on
  }

  private static JavaDependency junitEngineDependency() {
    return javaDependency()
      .groupId("org.junit.jupiter")
      .artifactId("junit-jupiter-engine")
      .versionSlug("junit-jupiter.version")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private static JavaDependency junitParamsDependency() {
    return javaDependency()
      .groupId("org.junit.jupiter")
      .artifactId("junit-jupiter-params")
      .versionSlug("junit-jupiter.version")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private static JavaDependency assertjDependency() {
    return javaDependency()
      .groupId("org.assertj")
      .artifactId("assertj-core")
      .versionSlug("assertj.version")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private static JavaDependency mockitoDependency() {
    return javaDependency()
      .groupId("org.mockito")
      .artifactId("mockito-junit-jupiter")
      .versionSlug("mockito.version")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  public JHipsterModule buildMavenWrapperModule(JHipsterModuleProperties properties, Version mavenVersion) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .put("latestMavenVersion", mavenVersion.version())
        .and()
      .startupCommands()
        .maven("")
        .and()
      .files()
        .addExecutable(SOURCE.file("mvnw"), to("mvnw"))
        .addExecutable(SOURCE.file("mvnw.cmd"), to("mvnw.cmd"))
        .batch(SOURCE.append(".mvn/wrapper"), to(".mvn/wrapper"))
          .addFile("maven-wrapper.jar")
          .addTemplate("maven-wrapper.properties")
          .and()
        .and()
      .build();
    //@formatter:on
  }

  private MavenPlugin mavenCompilerPlugin() {
    return mavenPlugin()
      .groupId(APACHE_PLUGINS_GROUP)
      .artifactId("maven-compiler-plugin")
      .versionSlug("compiler-plugin")
      .configuration(
        """
          <source>${java.version}</source>
          <target>${java.version}</target>
          <parameters>true</parameters>
        """
      )
      .build();
  }

  private MavenPlugin surefirePlugin() {
    return mavenPlugin()
      .groupId(APACHE_PLUGINS_GROUP)
      .artifactId("maven-surefire-plugin")
      .versionSlug("surefire-plugin")
      .configuration(
        """
        <!-- Force alphabetical order to have a reproducible build -->
        <runOrder>alphabetical</runOrder>
        <excludes>
          <exclude>**/*IT*</exclude>
          <exclude>**/*CucumberTest*</exclude>
        </excludes>
        """
      )
      .build();
  }

  private MavenPlugin failsafePlugin() {
    return mavenPlugin()
      .groupId(APACHE_PLUGINS_GROUP)
      .artifactId("maven-failsafe-plugin")
      .versionSlug("failsafe-plugin")
      .configuration(
        """
          <!-- Due to spring-boot repackage, without adding this property test classes are not found
                 See https://github.com/spring-projects/spring-boot/issues/6254 -->
          <classesDirectory>${project.build.outputDirectory}</classesDirectory>
          <!-- Force alphabetical order to have a reproducible build -->
          <runOrder>alphabetical</runOrder>
          <includes>
            <include>**/*IT*</include>
            <include>**/*CucumberTest*</include>
          </includes>
        """
      )
      .addExecution(pluginExecution().goals("integration-test").id("integration-test"))
      .addExecution(pluginExecution().goals("verify").id("verify"))
      .build();
  }

  private MavenPlugin enforcerPlugin() {
    return mavenPlugin().groupId(APACHE_PLUGINS_GROUP).artifactId(ENFORCER_ARTIFACTID).build();
  }

  private MavenPlugin enforcerPluginManagement() {
    return mavenPlugin()
      .groupId(APACHE_PLUGINS_GROUP)
      .artifactId(ENFORCER_ARTIFACTID)
      .versionSlug("maven-enforcer-plugin")
      .addExecution(pluginExecution().goals("enforce").id("enforce-versions"))
      .addExecution(
        pluginExecution()
          .goals("enforce")
          .id("enforce-dependencyConvergence")
          .configuration(
            """
            <rules>
              <DependencyConvergence />
            </rules>
            <fail>false</fail>
            """
          )
      )
      .configuration(
        """
        <rules>
          <requireMavenVersion>
            <message>You are running an older version of Maven: minimum required version is ${maven.version}</message>
            <version>${maven.version}</version>
          </requireMavenVersion>
          <requireJavaVersion>
            <message>You are running an incompatible version of Java: minimum required version is ${java.version}</message>
            <version>${java.version}</version>
          </requireJavaVersion>
        </rules>
        """
      )
      .build();
  }
}
