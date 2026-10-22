package com.seed4j.generator.server.javatool.nullaway.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class NullAwayModuleFactoryTest {

  private final NullAwayModuleFactory factory = new NullAwayModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing("<error-prone.version>")
      .containing(
        """
                <plugin>
                  <groupId>am.ik.maven</groupId>
                  <artifactId>nullability-maven-plugin</artifactId>
                  <version>${nullability-maven-plugin.version}</version>
                  <extensions>true</extensions>
                  <executions>
                    <execution>
                      <goals>
                        <goal>configure</goal>
                      </goals>
                    </execution>
                  </executions>
                  <configuration>
                    <errorProneVersion>${error-prone.version}</errorProneVersion>
                    <nullAwayVersion>${nullaway.version}</nullAwayVersion>
                    <outputDirectory>${project.basedir}/src/main/java</outputDirectory>
                    <testOutputDirectory>${project.basedir}/src/test/java</testOutputDirectory>
                  </configuration>
                </plugin>\
        """
      )
      .containing(
        """
              <plugin>
                <groupId>am.ik.maven</groupId>
                <artifactId>nullability-maven-plugin</artifactId>
              </plugin>\
        """
      );
  }
}
