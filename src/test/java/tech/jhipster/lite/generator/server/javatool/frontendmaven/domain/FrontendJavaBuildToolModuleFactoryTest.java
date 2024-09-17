package tech.jhipster.lite.generator.server.javatool.frontendmaven.domain;

import static org.mockito.Mockito.when;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.npm.*;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
@ExtendWith(MockitoExtension.class)
class FrontendJavaBuildToolModuleFactoryTest {

  @Mock
  private NpmVersions npmVersions;

  @InjectMocks
  private FrontendJavaBuildToolModuleFactory factory;

  @Test
  void shouldBuildFrontendMavenModule() {
    mockNpmVersion();

    JHipsterModule module = factory.buildFrontendMavenModule(getProperties());

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing("    <node.version>v16.0.0</node.version>")
      .containing("    <npm.version>4.0.0</npm.version>")
      .containing(
        """
              <plugin>
                <groupId>net.nicoulaj.maven.plugins</groupId>
                <artifactId>checksum-maven-plugin</artifactId>
                <version>${checksum-maven-plugin.version}</version>
                <executions>
                  <execution>
                    <id>create-pre-compiled-webapp-checksum</id>
                    <phase>generate-resources</phase>
                    <goals>
                      <goal>files</goal>
                    </goals>
                  </execution>
                  <execution>
                    <id>create-compiled-webapp-checksum</id>
                    <phase>compile</phase>
                    <goals>
                      <goal>files</goal>
                    </goals>
                    <configuration>
                      <csvSummaryFile>checksums.csv.old</csvSummaryFile>
                    </configuration>
                  </execution>
                </executions>
                <configuration>
                  <fileSets>
                    <fileSet>
                      <directory>${project.basedir}</directory>
                      <includes>
                        <include>src/main/webapp/**/*.*</include>
                        <include>target/classes/static/**/*.*</include>
                        <include>package-lock.json</include>
                        <include>package.json</include>
                        <include>tsconfig.json</include>
                      </includes>
                    </fileSet>
                  </fileSets>
                  <failOnError>false</failOnError>
                  <failIfNoFiles>false</failIfNoFiles>
                  <individualFiles>false</individualFiles>
                  <algorithms>
                    <algorithm>SHA-1</algorithm>
                  </algorithms>
                  <includeRelativePath>true</includeRelativePath>
                  <quiet>true</quiet>
                </configuration>
              </plugin>
        """
      )
      .containing(
        """
              <plugin>
                <artifactId>maven-antrun-plugin</artifactId>
                <version>${maven-antrun-plugin.version}</version>
                <executions>
                  <execution>
                    <id>eval-frontend-checksum</id>
                    <phase>generate-resources</phase>
                    <goals>
                      <goal>run</goal>
                    </goals>
                    <configuration>
                      <target>
                        <condition else="false" property="skip.npm" value="true">
                          <and>
                            <available file="checksums.csv" filepath="${project.build.directory}" />
                            <available file="checksums.csv.old" filepath="${project.build.directory}" />
                            <filesmatch file2="${project.build.directory}/checksums.csv.old" file1="${project.build.directory}/checksums.csv" />
                          </and>
                        </condition>
                      </target>
                      <exportAntProperties>true</exportAntProperties>
                    </configuration>
                  </execution>
                </executions>
              </plugin>
        """
      )
      .containing(
        """
              <plugin>
                <groupId>com.github.eirslett</groupId>
                <artifactId>frontend-maven-plugin</artifactId>
                <version>${frontend-maven-plugin.version}</version>
                <executions>
                  <execution>
                    <id>install-node-and-npm</id>
                    <goals>
                      <goal>install-node-and-npm</goal>
                    </goals>
                    <configuration>
                      <nodeVersion>${node.version}</nodeVersion>
                      <npmVersion>${npm.version}</npmVersion>
                    </configuration>
                  </execution>
                  <execution>
                    <id>npm install</id>
                    <goals>
                      <goal>npm</goal>
                    </goals>
                  </execution>
                  <execution>
                    <id>build front</id>
                    <phase>generate-resources</phase>
                    <goals>
                      <goal>npm</goal>
                    </goals>
                    <configuration>
                      <arguments>run build</arguments>
                      <environmentVariables>
                        <APP_VERSION>${project.version}</APP_VERSION>
                      </environmentVariables>
                      <npmInheritsProxyConfigFromMaven>false</npmInheritsProxyConfigFromMaven>
                    </configuration>
                  </execution>
                  <execution>
                    <id>front test</id>
                    <phase>test</phase>
                    <goals>
                      <goal>npm</goal>
                    </goals>
                    <configuration>
                      <arguments>run test:coverage</arguments>
                      <npmInheritsProxyConfigFromMaven>false</npmInheritsProxyConfigFromMaven>
                    </configuration>
                  </execution>
                </executions>
                <configuration>
                  <installDirectory>${project.build.directory}</installDirectory>
                </configuration>
              </plugin>
        """
      )
      .and()
      .hasPrefixedFiles(
        "src/main/java/tech/jhipster/jhlitest/wire/frontend",
        "infrastructure/primary/RedirectionResource.java",
        "package-info.java"
      );
  }

  @Test
  void shouldBuildFrontendGradleModule() {
    mockNpmVersion();

    JHipsterModule module = factory.buildFrontendGradleModule(getProperties());

    assertThatModuleWithFiles(module, gradleBuildFile(), gradleLibsVersionFile())
      .hasFile("gradle/libs.versions.toml")
      .containing("node-gradle = \"")
      .containing(
        """
        \t[plugins.node-gradle]
        \t\tid = "com.github.node-gradle.node"

        \t\t[plugins.node-gradle.version]
        \t\t\tref = "node-gradle"
        """
      )
      .and()
      .hasFile("build.gradle.kts")
      .containing(
        """
        import com.github.gradle.node.npm.task.NpmTask
        // jhipster-needle-gradle-imports
        """
      )
      .containing(
        """
          alias(libs.plugins.node.gradle)
          // jhipster-needle-gradle-plugins
        """
      )
      .containing(
        """
        val nodeVersionValue by extra("16.0.0")
        val npmVersionValue by extra("4.0.0")
        // jhipster-needle-gradle-properties
        """
      )
      .containing(
        """
        node {
          download.set(true)
          version.set(nodeVersionValue)
          npmVersion.set(npmVersionValue)
          workDir.set(file(layout.buildDirectory))
          npmWorkDir.set(file(layout.buildDirectory))
        }
        """
      )
      .containing(
        """
        val buildTaskUsingNpm = tasks.register<NpmTask>("buildNpm") {
          description = "Build the frontend project using NPM"
          group = "Build"
          dependsOn("npmInstall")
          npmCommand.set(listOf("run", "build"))
          environment.set(mapOf("APP_VERSION" to project.version.toString()))
        }
        """
      )
      .containing(
        """
        val testTaskUsingNpm = tasks.register<NpmTask>("testNpm") {
          description = "Test the frontend project using NPM"
          group = "verification"
          dependsOn("npmInstall", "buildNpm")
          npmCommand.set(listOf("run", "test:coverage"))
          ignoreExitValue.set(false)
          workingDir.set(projectDir)
          execOverrides {
            standardOutput = System.out
          }
        }
        """
      )
      .containing(
        """
        tasks.bootJar {
          dependsOn("buildNpm")
          from("build/classes/static") {
              into("BOOT-INF/classes/static")
          }
        }
        """
      )
      .containing(
        """
          dependsOn("testNpm")
          // jhipster-needle-gradle-tasks-test
        """
      );
  }

  private void mockNpmVersion() {
    when(npmVersions.get("npm", JHLiteNpmVersionSource.COMMON)).thenReturn(new NpmPackageVersion("4.0.0"));
    when(npmVersions.nodeVersion()).thenReturn(new NpmPackageVersion("16.0.0"));
  }

  private static @NotNull JHipsterModuleProperties getProperties() {
    return JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myapp")
      .build();
  }
}
