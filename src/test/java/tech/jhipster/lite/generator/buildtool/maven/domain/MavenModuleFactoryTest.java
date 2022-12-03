package tech.jhipster.lite.generator.buildtool.maven.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class MavenModuleFactoryTest {

  private static final MavenModuleFactory factory = new MavenModuleFactory();

  @Test
  void shouldBuildMavenModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myApp")
      .projectName("JHipster test")
      .build();

    JHipsterModule module = factory.buildMavenModule(properties);

    assertThatModuleWithFiles(module, readmeFile())
      .hasFile("pom.xml")
      .containing("<groupId>com.jhipster.test</groupId>")
      .containing("<artifactId>my-app</artifactId>")
      .containing("<name>myApp</name>")
      .containing("<description>JHipster test</description>")
      .containing(
        """
              <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${compiler-plugin.version}</version>
                <configuration>
                  <source>${java.version}</source>
                  <target>${java.version}</target>
                </configuration>
              </plugin>
        """
      )
      .containing(
        """
              <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>${surefire-plugin.version}</version>
                <configuration>
                  <!-- Force alphabetical order to have a reproducible build -->
                  <runOrder>alphabetical</runOrder>
                  <excludes>
                    <exclude>**/*IT*</exclude>
                    <exclude>**/*IntTest*</exclude>
                    <exclude>**/*CucumberTest*</exclude>
                  </excludes>
                </configuration>
              </plugin>
        """
      )
      .containing(
        """
              <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>${jacoco.version}</version>
              </plugin>
        """
      )
      .containing(
        """
              <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-failsafe-plugin</artifactId>
                <version>${failsafe-plugin.version}</version>
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
                </plugin>
        """
      )
      .and()
      .hasExecutableFiles("mvnw", "mvnw.cmd")
      .hasPrefixedFiles(".mvn/wrapper", "maven-wrapper.jar", "maven-wrapper.properties")
      .hasFile("README.md")
      .containing("./mvnw");
  }

  @Test
  void shouldBuildMavenWrapperModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildMavenWrapperModule(properties);

    assertThatModuleWithFiles(module)
      .hasExecutableFiles("mvnw", "mvnw.cmd")
      .hasPrefixedFiles(".mvn/wrapper", "maven-wrapper.jar", "maven-wrapper.properties");
  }
}
