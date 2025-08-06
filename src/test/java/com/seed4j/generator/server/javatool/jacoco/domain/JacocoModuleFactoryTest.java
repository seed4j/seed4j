package com.seed4j.generator.server.javatool.jacoco.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class JacocoModuleFactoryTest {

  private static final JacocoModuleFactory factory = new JacocoModuleFactory();

  @Nested
  class Maven {

    @Test
    void shouldBuildJacocoModule() {
      JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

      JHipsterModule module = factory.buildJacocoModule(properties);

      assertThatModuleWithFiles(module, pomFile())
        .hasFile("pom.xml")
        .containing(
          """
                <plugin>
                  <groupId>org.jacoco</groupId>
                  <artifactId>jacoco-maven-plugin</artifactId>
                </plugin>
          """
        )
        .containing(
          """
                  <plugin>
                    <groupId>org.jacoco</groupId>
                    <artifactId>jacoco-maven-plugin</artifactId>
                    <version>${jacoco.version}</version>
                    <executions>
                      <execution>
                        <id>pre-unit-tests</id>
                        <goals>
                          <goal>prepare-agent</goal>
                        </goals>
                      </execution>
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
                  </plugin>
          """
        );
    }

    @Test
    void shouldBuildJacocoWithMinCoverageCheckModule() {
      JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

      JHipsterModule module = factory.buildJacocoWithMinCoverageCheckModule(properties);

      assertThatModuleWithFiles(module, pomFile())
        .hasFile("pom.xml")
        .containing(
          """
                      <execution>
                        <id>check</id>
                        <goals>
                          <goal>check</goal>
                        </goals>
                        <configuration>
                          <dataFile>target/jacoco/allTest.exec</dataFile>
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
                        </configuration>
                      </execution>
                    </executions>
                  </plugin>
          """
        );
    }
  }

  @Nested
  class Gradle {

    @Test
    void shouldBuildJacocoModule() {
      JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

      JHipsterModule module = factory.buildJacocoModule(properties);

      assertThatModuleWithFiles(module, gradleBuildFile())
        .hasFile("build.gradle.kts")
        .containing(
          """
            jacoco
            // seed4j-needle-gradle-plugins
          """
        )
        .containing(
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
        .containing(
          """
            finalizedBy("jacocoTestReport")
            // seed4j-needle-gradle-tasks-test
          """
        )
        .and()
        .hasFile("gradle/libs.versions.toml")
        .containing("jacoco = \"");
    }
  }

  @Test
  void shouldBuildJacocoWithMinCoverageCheckModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildJacocoWithMinCoverageCheckModule(properties);

    assertThatModuleWithFiles(module, gradleBuildFile())
      .hasFile("build.gradle.kts")
      .containing(
        """
          jacoco
          // seed4j-needle-gradle-plugins
        """
      )
      .containing(
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
      .containing(
        """
          finalizedBy("jacocoTestCoverageVerification")
          // seed4j-needle-gradle-tasks-test
        """
      )
      .and()
      .hasFile("gradle/libs.versions.toml")
      .containing("jacoco = \"");
  }
}
