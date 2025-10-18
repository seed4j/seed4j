package com.seed4j.generator.buildtool.maven.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.readmeFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class MavenModuleFactoryTest {

  private static final MavenModuleFactory factory = new MavenModuleFactory();

  @Test
  void shouldBuildMavenModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myApp")
      .projectName("Seed4J test")
      .build();

    Seed4JModule module = factory.buildMavenModule(properties);

    assertThatModuleWithFiles(module, readmeFile())
      .hasFile("pom.xml")
      .containing("<groupId>com.seed4j.growth</groupId>")
      .containing("<artifactId>my-app</artifactId>")
      .containing("<name>myApp</name>")
      .containing("<description>Seed4J test</description>")
      .containing(
        """
              <dependency>
                <groupId>org.junit</groupId>
                <artifactId>junit-bom</artifactId>
                <version>${junit-jupiter.version}</version>
                <type>pom</type>
                <scope>import</scope>
              </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.junit.jupiter</groupId>
              <artifactId>junit-jupiter-engine</artifactId>
              <scope>test</scope>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.junit.jupiter</groupId>
              <artifactId>junit-jupiter-params</artifactId>
              <scope>test</scope>
            </dependency>
        """
      )
      .containing(
        """
              <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${compiler-plugin.version}</version>
                <configuration>
                  <release>${java.version}</release>
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

        You need to have Java 25:

        - [JDK 25](https://openjdk.java.net/projects/jdk/25/)
        """
      );
  }

  @Test
  void shouldDeclareFailsafePluginAfterSurefirePluginInPomXml() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myApp")
      .projectName("Seed4J test")
      .build();

    Seed4JModule module = factory.buildMavenModule(properties);

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
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildMavenWrapperModule(properties);

    assertThatModuleWithFiles(module, pomFile(), readmeFile())
      .hasFile(".gitignore")
      .containing(
        """
        # Maven Wrapper
        !.mvn/wrapper/maven-wrapper.jar\
        """
      )
      .and()
      .hasExecutableFiles("mvnw", "mvnw.cmd")
      .hasPrefixedFiles(".mvn/wrapper", "maven-wrapper.jar", "maven-wrapper.properties")
      .hasFile("README.md")
      .containing("./mvnw");
  }
}
