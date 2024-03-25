package tech.jhipster.lite.generator.buildtool.maven.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.javadependency.Version;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class MavenModuleFactoryTest {

  private static final MavenModuleFactory factory = new MavenModuleFactory();
  private static final Version MAVEN_VERSION = new Version("3.9.6");

  @Test
  void shouldBuildMavenModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myApp")
      .projectName("JHipster test")
      .build();

    JHipsterModule module = factory.buildMavenModule(properties, MAVEN_VERSION);

    assertThatModuleWithFiles(module, readmeFile())
      .hasFile("pom.xml")
      .containing("<groupId>com.jhipster.test</groupId>")
      .containing("<artifactId>my-app</artifactId>")
      .containing("<name>myApp</name>")
      .containing("<description>JHipster test</description>")
      .containing(
        """
              <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${compiler-plugin.version}</version>
                <configuration>
                  <source>${java.version}</source>
                  <target>${java.version}</target>
                  <parameters>true</parameters>
                </configuration>
              </plugin>
        """
      )
      .containing(
        """
              <plugin>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>${surefire-plugin.version}</version>
                <configuration>
                  <runOrder>alphabetical</runOrder>
                  <excludes>
                    <exclude>**/*IT*</exclude>
                    <exclude>**/*CucumberTest*</exclude>
                  </excludes>
                </configuration>
              </plugin>
        """
      )
      .containing(
        """
              <plugin>
                <artifactId>maven-failsafe-plugin</artifactId>
                <version>${failsafe-plugin.version}</version>
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
                <configuration>
                  <classesDirectory>${project.build.outputDirectory}</classesDirectory>
                  <runOrder>alphabetical</runOrder>
                  <includes>
                    <include>**/*IT*</include>
                    <include>**/*CucumberTest*</include>
                  </includes>
                </configuration>
              </plugin>
        """
      )
      .containing(
        """
                <plugin>
                  <artifactId>maven-enforcer-plugin</artifactId>
                  <version>${maven-enforcer-plugin.version}</version>
                  <executions>
                    <execution>
                      <id>enforce-versions</id>
                      <goals>
                        <goal>enforce</goal>
                      </goals>
                    </execution>
                    <execution>
                      <id>enforce-dependencyConvergence</id>
                      <goals>
                        <goal>enforce</goal>
                      </goals>
                      <configuration>
                        <rules>
                          <DependencyConvergence />
                        </rules>
                        <fail>false</fail>
                      </configuration>
                    </execution>
                  </executions>
                  <configuration>
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
                  </configuration>
                </plugin>
        """
      )
      .containing(
        """
              <plugin>
                <artifactId>maven-enforcer-plugin</artifactId>
              </plugin>
        """
      )
      .and()
      .hasFile("README.md")
      .containing(
        """
        ### Java

        You need to have Java 21:
        - [JDK 21](https://openjdk.java.net/projects/jdk/21/)
        """
      );
  }

  @Test
  void shouldDeclareFailsafePluginAfterSurefirePluginInPomXml() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myApp")
      .projectName("JHipster test")
      .build();

    JHipsterModule module = factory.buildMavenModule(properties, MAVEN_VERSION);

    assertThatModuleWithFiles(module, readmeFile())
      .hasFile("pom.xml")
      .containingInSequence(
        "</pluginManagement>",
        "<plugins>",
        "<artifactId>maven-surefire-plugin</artifactId>",
        "<artifactId>maven-failsafe-plugin</artifactId>"
      );
  }

  @Test
  void shouldBuildMavenWrapperModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildMavenWrapperModule(properties, MAVEN_VERSION);

    assertThatModuleWithFiles(module, pomFile(), readmeFile())
      .hasExecutableFiles("mvnw", "mvnw.cmd")
      .hasPrefixedFiles(".mvn/wrapper", "maven-wrapper.jar", "maven-wrapper.properties")
      .hasFile("README.md")
      .containing("./mvnw");
  }
}
