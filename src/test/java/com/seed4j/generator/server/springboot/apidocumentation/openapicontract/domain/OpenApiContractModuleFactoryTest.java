package com.seed4j.generator.server.springboot.apidocumentation.openapicontract.domain;

import static com.seed4j.TestFileUtils.tmpDirForTest;
import static com.seed4j.module.domain.JHipsterModulesFixture.propertiesBuilder;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class OpenApiContractModuleFactoryTest {

  private static final OpenApiContractModuleFactory factory = new OpenApiContractModuleFactory();

  @Test
  void shouldBuildOpenApiContractModule() {
    JHipsterModuleProperties properties = propertiesBuilder(tmpDirForTest()).basePackage("com.seed4j.growth").build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        // language=xml
        """
              <plugin>
                <groupId>io.github.kbuntrock</groupId>
                <artifactId>openapi-maven-plugin</artifactId>
              </plugin>\
        """
      )
      .containing(
        // language=xml
        """
                <plugin>
                  <groupId>io.github.kbuntrock</groupId>
                  <artifactId>openapi-maven-plugin</artifactId>
                  <version>${openapi-maven-plugin.version}</version>
                  <executions>
                    <execution>
                      <id>generate-openapi-contract</id>
                      <goals>
                        <goal>documentation</goal>
                      </goals>
                    </execution>
                  </executions>
                  <configuration>
                    <apiConfiguration>
                      <library>SPRING_MVC</library>
                    </apiConfiguration>
                    <apis>
                      <api>
                        <filename>openapi-contract.yml</filename>
                        <locations>
                          <location>com.seed4j.growth</location>
                        </locations>
                        <tag>
                          <substitutions>
                            <sub>
                              <regex>Resource$</regex>
                              <substitute />
                            </sub>
                          </substitutions>
                        </tag>
                      </api>
                    </apis>
                  </configuration>
                </plugin>\
        """
      );
  }

  @Test
  void shouldBuildOpenApiBackwardsCompatibilityCheckModule() {
    JHipsterModuleProperties properties = propertiesBuilder(tmpDirForTest()).build();

    JHipsterModule module = factory.buildBackwardsCompatibilityCheckModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        // language=xml
        """
              <plugin>
                <groupId>io.kemtoa.openapi</groupId>
                <artifactId>openapi-backwards-compat-maven-plugin</artifactId>
              </plugin>\
        """
      )
      .containing(
        // language=xml
        """
                <plugin>
                  <groupId>io.kemtoa.openapi</groupId>
                  <artifactId>openapi-backwards-compat-maven-plugin</artifactId>
                  <version>${openapi-backwards-compat-maven-plugin.version}</version>
                  <executions>
                    <execution>
                      <goals>
                        <goal>backwards-compatibility-check</goal>
                      </goals>
                    </execution>
                  </executions>
                  <configuration>
                    <openApiSourceDir>${project.build.directory}</openApiSourceDir>
                  </configuration>
                </plugin>\
        """
      );
  }
}
