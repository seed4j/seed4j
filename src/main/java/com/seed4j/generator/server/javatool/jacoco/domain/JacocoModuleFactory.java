package com.seed4j.generator.server.javatool.jacoco.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.gradleCorePlugin;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.mavenPlugin;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.pluginExecution;
import static com.seed4j.module.domain.Seed4JModule.versionSlug;
import static com.seed4j.module.domain.mavenplugin.MavenBuildPhase.POST_INTEGRATION_TEST;
import static com.seed4j.module.domain.mavenplugin.MavenBuildPhase.TEST;
import static com.seed4j.module.domain.mavenplugin.MavenBuildPhase.VERIFY;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.gradleplugin.GradleMainBuildPlugin;
import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.mavenplugin.MavenPlugin.MavenPluginOptionalBuilder;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class JacocoModuleFactory {

  private static final String JACOCO = "jacoco";
  private static final GroupId JACOCO_GROUP = groupId("org.jacoco");
  private static final ArtifactId JACOCO_ARTIFACT_ID = artifactId("jacoco-maven-plugin");
  private static final VersionSlug JACOCO_VERSION = versionSlug(JACOCO);

  public Seed4JModule buildJacocoModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .mavenPlugins()
        .plugin(mavenJacocoPlugin())
        .pluginManagement(mavenJacocoPluginManagement())
        .and()
      .gradlePlugins()
        .plugin(gradleJacocoPlugin())
        .and()
      .gradleConfigurations()
        .addTasksTestInstruction(
          """
          finalizedBy("jacocoTestReport")\
          """
        )
        .and()
      .build();
    // @formatter:on
  }

  public Seed4JModule buildJacocoWithMinCoverageCheckModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .mavenPlugins()
        .plugin(mavenJacocoPlugin())
        .pluginManagement(mavenJacocoWithMinCoverageCheckPluginManagement(properties))
        .and()
      .gradlePlugins()
        .plugin(gradleJacocoWithMinCoverageCheckPlugin())
        .and()
      .gradleConfigurations()
        .addTasksTestInstruction(
          """
          finalizedBy("jacocoTestCoverageVerification")\
          """
        )
        .and()
      .build();
    // @formatter:on
  }

  private static MavenPlugin mavenJacocoPlugin() {
    return mavenPlugin().groupId(JACOCO_GROUP).artifactId(JACOCO_ARTIFACT_ID).build();
  }

  private static MavenPlugin mavenJacocoPluginManagement() {
    return commonMavenJacocoPluginManagement().build();
  }

  private static MavenPlugin mavenJacocoWithMinCoverageCheckPluginManagement(Seed4JModuleProperties properties) {
    String basePackagePath = properties.basePackage().get().replace(".", "/");
    return commonMavenJacocoPluginManagement()
      .addExecution(
        pluginExecution()
          .goals("check")
          .id("check")
          .configuration(
            """
              <dataFile>target/jacoco/allTest.exec</dataFile>
              <includes>
                <include>%s/**</include>
              </includes>
              <excludes>
                <exclude>%s/**/infrastructure/secondary/**/*Entity_.class</exclude>
              </excludes>
              <rules>
                <rule>
                  <element>CLASS</element>
                  <limits>
                    <limit>
                      <counter>BRANCH</counter>
                      <value>MISSEDCOUNT</value>
                      <maximum>0</maximum>
                    </limit>
                    <limit>
                      <counter>LINE</counter>
                      <value>MISSEDCOUNT</value>
                      <maximum>0</maximum>
                    </limit>
                  </limits>
                </rule>
              </rules>
            """.formatted(basePackagePath, basePackagePath)
          )
      )
      .build();
  }

  private static MavenPluginOptionalBuilder commonMavenJacocoPluginManagement() {
    return mavenPlugin()
      .groupId(JACOCO_GROUP)
      .artifactId(JACOCO_ARTIFACT_ID)
      .versionSlug(JACOCO_VERSION)
      .addExecution(pluginExecution().goals("prepare-agent").id("pre-unit-tests"))
      .addExecution(pluginExecution().goals("report").id("post-unit-test").phase(TEST))
      .addExecution(pluginExecution().goals("prepare-agent-integration").id("pre-integration-tests"))
      .addExecution(pluginExecution().goals("report-integration").id("post-integration-tests").phase(POST_INTEGRATION_TEST))
      .addExecution(
        pluginExecution()
          .goals("merge")
          .id("merge")
          .phase(VERIFY)
          .configuration(
            """
              <fileSets>
              <fileSet implementation="org.apache.maven.shared.model.fileset.FileSet">
                <directory>${project.basedir}</directory>
                <includes>
                  <include>**/*.exec</include>
                </includes>
              </fileSet>
            </fileSets>
            <destFile>target/jacoco/allTest.exec</destFile>
            """
          )
      )
      .addExecution(
        pluginExecution()
          .goals("report")
          .id("post-merge-report")
          .phase(VERIFY)
          .configuration(
            """
              <dataFile>target/jacoco/allTest.exec</dataFile>
              <outputDirectory>target/jacoco/</outputDirectory>
            """
          )
      );
  }

  private static GradleMainBuildPlugin gradleJacocoPlugin() {
    return gradleCorePlugin()
      .id(JACOCO)
      .toolVersionSlug(JACOCO)
      .configuration(
        """
        jacoco {
          toolVersion = libs.versions.jacoco.get()
        }

        tasks.jacocoTestReport {
          dependsOn("test", "integrationTest")
          reports {
            xml.required.set(true)
            html.required.set(true)
          }
          executionData.setFrom(fileTree(layout.buildDirectory).include("**/jacoco/test.exec", "**/jacoco/integrationTest.exec"))
        }
        """
      )
      .build();
  }

  private static GradleMainBuildPlugin gradleJacocoWithMinCoverageCheckPlugin() {
    return gradleCorePlugin()
      .id(JACOCO)
      .toolVersionSlug(JACOCO)
      .configuration(
        """
        jacoco {
          toolVersion = libs.versions.jacoco.get()
        }

        tasks.jacocoTestReport {
          dependsOn("test", "integrationTest")
          reports {
            xml.required.set(true)
            html.required.set(true)
          }
          executionData.setFrom(fileTree(layout.buildDirectory).include("**/jacoco/test.exec", "**/jacoco/integrationTest.exec"))
        }

        tasks.jacocoTestCoverageVerification {
          dependsOn("jacocoTestReport")
          violationRules {

              rule {
                  element = "CLASS"

                  limit {
                      counter = "LINE"
                      value = "MISSEDCOUNT"
                      maximum = "0.00".toBigDecimal()
                  }

                  limit {
                      counter = "BRANCH"
                      value = "MISSEDCOUNT"
                      maximum = "0.00".toBigDecimal()
                  }
              }
          }
          executionData.setFrom(fileTree(layout.buildDirectory).include("**/jacoco/test.exec", "**/jacoco/integrationTest.exec"))
        }
        """
      )
      .build();
  }
}
