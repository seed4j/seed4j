package com.seed4j.generator.server.javatool.frontendmaven.domain;

import static com.seed4j.module.domain.nodejs.NodePackageManager.NPM;
import static com.seed4j.module.domain.nodejs.NodePackageManager.PNPM;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.gradleBuildFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.gradleLibsVersionFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;
import static org.mockito.Mockito.when;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.nodejs.NodePackageVersion;
import com.seed4j.module.domain.nodejs.NodeVersions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class FrontendJavaBuildToolModuleFactoryTest {

  @Mock
  private NodeVersions nodeVersions;

  @InjectMocks
  private FrontendJavaBuildToolModuleFactory factory;

  @Nested
  class Maven {

    @Test
    void shouldBuildFrontendMavenModuleWithNpm() {
      mockNpmVersion();
      mockNodeVersion();

      Seed4JModule module = factory.buildFrontendMavenModule(defaultProperties().nodePackageManager(NPM).build());

      assertThatModuleWithFiles(module, pomFile())
        .hasFile("pom.xml")
        .containing("    <node.version>v16.0.0</node.version>")
        .containing("    <npm.version>4.0.0</npm.version>")
        .notContaining("<artifactId>checksum-maven-plugin</artifactId>")
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
          "src/main/java/com/seed4j/growth/wire/frontend",
          "infrastructure/primary/RedirectionResource.java",
          "package-info.java"
        );
    }

    @Test
    void shouldBuildFrontendMavenModuleWithPnpm() {
      mockNodeVersion();

      Seed4JModule module = factory.buildFrontendMavenModule(defaultProperties().nodePackageManager(PNPM).build());

      assertThatModuleWithFiles(module, pomFile())
        .hasFile("pom.xml")
        .containing("    <node.version>v16.0.0</node.version>")
        .notContaining("<npm.version>")
        .containing(
          """
                <plugin>
                  <groupId>com.github.eirslett</groupId>
                  <artifactId>frontend-maven-plugin</artifactId>
                  <version>${frontend-maven-plugin.version}</version>
                  <executions>
                    <execution>
                      <id>install-node-and-corepack</id>
                      <goals>
                        <goal>install-node-and-corepack</goal>
                      </goals>
                      <configuration>
                        <nodeVersion>${node.version}</nodeVersion>
                      </configuration>
                    </execution>
                    <execution>
                      <id>pnpm install</id>
                      <goals>
                        <goal>corepack</goal>
                      </goals>
                      <configuration>
                        <arguments>pnpm install</arguments>
                      </configuration>
                    </execution>
                    <execution>
                      <id>build front</id>
                      <phase>generate-resources</phase>
                      <goals>
                        <goal>corepack</goal>
                      </goals>
                      <configuration>
                        <arguments>pnpm build</arguments>
                        <environmentVariables>
                          <APP_VERSION>${project.version}</APP_VERSION>
                        </environmentVariables>
                      </configuration>
                    </execution>
                    <execution>
                      <id>front test</id>
                      <phase>test</phase>
                      <goals>
                        <goal>corepack</goal>
                      </goals>
                      <configuration>
                        <arguments>pnpm test:coverage</arguments>
                      </configuration>
                    </execution>
                  </executions>
                  <configuration>
                    <installDirectory>${project.build.directory}</installDirectory>
                  </configuration>
                </plugin>
          """
        );
    }

    @Test
    void shouldBuildFrontendMavenCacheModule() {
      Seed4JModule module = factory.buildFrontendMavenCacheModule(defaultProperties().build());

      assertThatModuleWithFiles(module, pomFile())
        .hasFile("pom.xml")
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
        );
    }

    @Test
    void shouldBuildFrontendMavenCacheModuleWithPnpm() {
      Seed4JModule module = factory.buildFrontendMavenCacheModule(defaultProperties().nodePackageManager(PNPM).build());

      assertThatModuleWithFiles(module, pomFile())
        .hasFile("pom.xml")
        .containing(
          """
                          <condition else="false" property="skip.corepack" value="true">
          """
        );
    }

    @Test
    void shouldBuildMergeCypressCoverageModuleWithNpm() {
      Seed4JModule module = factory.buildMergeCypressCoverageModule(defaultProperties().nodePackageManager(NPM).build());

      assertThatModuleWithFiles(module, pomFile())
        .hasFile("pom.xml")
        .containing("run test:coverage")
        .containing("run test:component:headless")
        .containing("run test:coverage:check");
    }

    @Test
    void shouldBuildMergeCypressCoverageModuleWithPnpm() {
      Seed4JModule module = factory.buildMergeCypressCoverageModule(defaultProperties().nodePackageManager(PNPM).build());

      assertThatModuleWithFiles(module, pomFile())
        .hasFile("pom.xml")
        .containing("pnpm test:coverage")
        .containing("pnpm test:component:headless")
        .containing("pnpm test:coverage:check");
    }
  }

  @Nested
  class Gradle {

    @Test
    void shouldBuildFrontendGradleModuleWithNpm() {
      mockNodeVersion();
      mockNpmVersion();

      Seed4JModule module = factory.buildFrontendGradleModule(defaultProperties().nodePackageManager(NPM).build());

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
          // seed4j-needle-gradle-imports
          """
        )
        .containing(
          """
            alias(libs.plugins.node.gradle)
            // seed4j-needle-gradle-plugins
          """
        )
        .containing(
          """
          val nodeVersionValue by extra("16.0.0")
          val npmVersionValue by extra("4.0.0")
          // seed4j-needle-gradle-properties
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
            description = "Build the frontend project using npm"
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
            description = "Test the frontend project using npm"
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
            // seed4j-needle-gradle-tasks-test
          """
        );
    }

    @Test
    void shouldBuildFrontendGradleModuleWithPnpm() {
      mockNodeVersion();
      mockPnpmVersion();

      Seed4JModule module = factory.buildFrontendGradleModule(defaultProperties().nodePackageManager(PNPM).build());

      assertThatModuleWithFiles(module, gradleBuildFile(), gradleLibsVersionFile())
        .hasFile("build.gradle.kts")
        .containing(
          """
          import com.github.gradle.node.pnpm.task.PnpmTask
          // seed4j-needle-gradle-imports
          """
        )
        .containing(
          """
            alias(libs.plugins.node.gradle)
            // seed4j-needle-gradle-plugins
          """
        )
        .containing(
          """
          val nodeVersionValue by extra("16.0.0")
          val pnpmVersionValue by extra("10.0.0")
          // seed4j-needle-gradle-properties
          """
        )
        .containing(
          """
          node {
            download.set(true)
            version.set(nodeVersionValue)
            pnpmVersion.set(pnpmVersionValue)
            workDir.set(file(layout.buildDirectory))
            pnpmWorkDir.set(file(layout.buildDirectory))
          }
          """
        )
        .containing(
          """
          val buildTaskUsingPnpm = tasks.register<PnpmTask>("buildPnpm") {
            description = "Build the frontend project using pnpm"
            group = "Build"
            dependsOn("pnpmInstall")
            pnpmCommand.set(listOf("run", "build"))
            environment.set(mapOf("APP_VERSION" to project.version.toString()))
          }
          """
        )
        .containing(
          """
          val testTaskUsingPnpm = tasks.register<PnpmTask>("testPnpm") {
            description = "Test the frontend project using pnpm"
            group = "verification"
            dependsOn("pnpmInstall", "buildPnpm")
            pnpmCommand.set(listOf("run", "test:coverage"))
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
            dependsOn("buildPnpm")
            from("build/classes/static") {
                into("BOOT-INF/classes/static")
            }
          }
          """
        )
        .containing(
          """
            dependsOn("testPnpm")
            // seed4j-needle-gradle-tasks-test
          """
        );
    }
  }

  private void mockNodeVersion() {
    when(nodeVersions.nodeVersion()).thenReturn(new NodePackageVersion("16.0.0"));
  }

  private void mockNpmVersion() {
    when(nodeVersions.packageManagerVersion(NPM)).thenReturn(new NodePackageVersion("4.0.0"));
  }

  private void mockPnpmVersion() {
    when(nodeVersions.packageManagerVersion(PNPM)).thenReturn(new NodePackageVersion("10.0.0"));
  }

  private static Seed4JModulesFixture.Seed4JModulePropertiesBuilder defaultProperties() {
    return Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).basePackage("com.seed4j.growth").projectBaseName("myapp");
  }
}
