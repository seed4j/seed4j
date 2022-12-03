package tech.jhipster.lite.generator.buildtool.maven.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class MavenModuleFactory {

  private static final JHipsterSource SOURCE = from("buildtool/maven");

  private static final GroupId APACHE_PLUGINS_GROUP = groupId("org.apache.maven.plugins");

  private static final GroupId JACOCO_GROUP = groupId("org.jacoco");
  private static final ArtifactId JACOCO_ARTIFACT_ID = artifactId("jacoco-maven-plugin");
  private static final VersionSlug JACOCO_VERSION = versionSlug("jacoco");

  public JHipsterModule buildMavenModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return mavenWrapperModulesFiles(properties)
      .context()
        .put("dasherizedBaseName", properties.projectBaseName().kebabCase())
        .and()
      .startupCommand("./mvnw")
      .files()
        .add(SOURCE.template("pom.xml"), to("pom.xml"))
        .and()
      .javaBuildPlugins()
        .plugin(mavenCompilerPlugin())
        .plugin(surefirePlugin())
        .plugin(jacocoPlugin())
        .plugin(failsafePlugin())
        .pluginManagement(jacocoPluginManagement())
        .pluginManagement(enforcerPluginManagement())
        .and()
      .build();
    //@formatter:on
  }

  public JHipsterModule buildMavenWrapperModule(JHipsterModuleProperties properties) {
    return mavenWrapperModulesFiles(properties).build();
  }

  private JHipsterModuleBuilder mavenWrapperModulesFiles(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .addExecutable(SOURCE.file("mvnw"), to("mvnw"))
        .addExecutable(SOURCE.file("mvnw.cmd"), to("mvnw.cmd"))
        .batch(SOURCE.append(".mvn/wrapper"), to(".mvn/wrapper"))
        .addFile("maven-wrapper.jar")
        .addFile("maven-wrapper.properties")
        .and()
      .and();
    //@formatter:on
  }

  private JavaBuildPlugin mavenCompilerPlugin() {
    return javaBuildPlugin()
      .groupId(APACHE_PLUGINS_GROUP)
      .artifactId("maven-compiler-plugin")
      .versionSlug("compiler-plugin")
      .additionalElements(
        """
        <configuration>
          <source>${java.version}</source>
          <target>${java.version}</target>
        </configuration>
        """
      )
      .build();
  }

  private JavaBuildPlugin surefirePlugin() {
    return javaBuildPlugin()
      .groupId(APACHE_PLUGINS_GROUP)
      .artifactId("maven-surefire-plugin")
      .versionSlug("surefire-plugin")
      .additionalElements(
        """
        <configuration>
          <!-- Force alphabetical order to have a reproducible build -->
          <runOrder>alphabetical</runOrder>
          <excludes>
            <exclude>**/*IT*</exclude>
            <exclude>**/*IntTest*</exclude>
            <exclude>**/*CucumberTest*</exclude>
          </excludes>
        </configuration>
        """
      )
      .build();
  }

  private JavaBuildPlugin jacocoPlugin() {
    return javaBuildPlugin().groupId(JACOCO_GROUP).artifactId(JACOCO_ARTIFACT_ID).versionSlug(JACOCO_VERSION).build();
  }

  private JavaBuildPlugin failsafePlugin() {
    return javaBuildPlugin()
      .groupId(APACHE_PLUGINS_GROUP)
      .artifactId("maven-failsafe-plugin")
      .versionSlug("failsafe-plugin")
      .additionalElements(
        """
        <configuration>
          <!-- Due to spring-boot repackage, without adding this property test classes are not found
                 See https://github.com/spring-projects/spring-boot/issues/6254 -->
          <classesDirectory>${project.build.outputDirectory}</classesDirectory>
          <!-- Force alphabetical order to have a reproducible build -->
          <runOrder>alphabetical</runOrder>
          <includes>
            <include>**/*IT*</include>
            <include>**/*IntTest*</include>
            <include>**/*CucumberTest*</include>
          </includes>
        </configuration>
        <executions>
          <execution>
            <id>integration-test</id>
            <goals>
              <goal>integration-test</goal>
            </goals>
          </execution>
          <execution>
            <id>verify</id>
            <goals>
              <goal>verify</goal>
            </goals>
          </execution>
        </executions>
        """
      )
      .build();
  }

  private JavaBuildPlugin jacocoPluginManagement() {
    return javaBuildPlugin()
      .groupId(JACOCO_GROUP)
      .artifactId(JACOCO_ARTIFACT_ID)
      .versionSlug(JACOCO_VERSION)
      .additionalElements(
        """
        <executions>
          <execution>
            <id>pre-unit-tests</id>
            <goals>
              <goal>prepare-agent</goal>
            </goals>
          </execution>
          <!-- Ensures that the code coverage report for unit tests is created after unit tests have been run -->
          <execution>
            <id>post-unit-test</id>
            <phase>test</phase>
            <goals>
              <goal>report</goal>
            </goals>
          </execution>
          <execution>
            <id>pre-integration-tests</id>
            <goals>
              <goal>prepare-agent-integration</goal>
            </goals>
          </execution>
          <!-- Ensures that the code coverage report for integration tests is created after integration tests have been run -->
          <execution>
            <id>post-integration-tests</id>
            <phase>post-integration-test</phase>
            <goals>
              <goal>report-integration</goal>
            </goals>
          </execution>
          <execution>
            <id>merge</id>
            <phase>verify</phase>
            <goals>
              <goal>merge</goal>
            </goals>
            <configuration>
              <fileSets>
                <fileSet implementation="org.apache.maven.shared.model.fileset.FileSet">
                  <directory>${project.basedir}</directory>
                  <includes>
                    <include>**/*.exec</include>
                  </includes>
                </fileSet>
              </fileSets>
              <destFile>target/jacoco/allTest.exec</destFile>
            </configuration>
          </execution>
          <execution>
            <id>post-merge-report</id>
            <phase>verify</phase>
            <goals>
              <goal>report</goal>
            </goals>
            <configuration>
              <dataFile>target/jacoco/allTest.exec</dataFile>
              <outputDirectory>target/jacoco/</outputDirectory>
            </configuration>
          </execution>
        </executions>
        """
      )
      .build();
  }

  private JavaBuildPlugin enforcerPluginManagement() {
    return javaBuildPlugin()
      .groupId(APACHE_PLUGINS_GROUP)
      .artifactId("maven-enforcer-plugin")
      .versionSlug("maven-enforcer-plugin")
      .additionalElements(
        """
        <executions>
          <execution>
            <id>enforce-versions</id>
            <goals>
              <goal>enforce</goal>
            </goals>
          </execution>
          <execution>
            <id>enforce-dependencyConvergence</id>
            <configuration>
              <rules>
                <DependencyConvergence />
              </rules>
              <fail>false</fail>
            </configuration>
            <goals>
              <goal>enforce</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <rules>
            <requireMavenVersion>
              <message>You are running an older version of Maven. JHipster requires at least Maven ${maven.version}</message>
              <version>[${maven.version},)</version>
            </requireMavenVersion>
            <requireJavaVersion>
              <message>You are running an incompatible version of Java. JHipster supports JDK 17.</message>
              <version>[17,18)</version>
            </requireJavaVersion>
          </rules>
        </configuration>
        """
      )
      .build();
  }
}
