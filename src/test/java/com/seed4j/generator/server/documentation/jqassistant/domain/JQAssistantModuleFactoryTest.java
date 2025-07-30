package com.seed4j.generator.server.documentation.jqassistant.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions;
import org.junit.jupiter.api.Test;

@UnitTest
class JQAssistantModuleFactoryTest {

  private static final JQAssistantModuleFactory factory = new JQAssistantModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile(".jqassistant.yml")
      .containing("artifact-id: jqassistant-context-mapper-plugin")
      .containing("# jhipster-needle-jqassistant-plugin")
      .containing("# jhipster-needle-jqassistant-analyze-concept")
      .containing("# jhipster-needle-jqassistant-analyze-group")
      .and()
      .hasFile("pom.xml")
      // jQAssistant
      .containing("<jqassistant-context-mapper-plugin.version>")
      .containing(
        """
                <plugin>
                  <groupId>com.buschmais.jqassistant</groupId>
                  <artifactId>jqassistant-maven-plugin</artifactId>
                  <version>${jqassistant.version}</version>
                  <executions>
                    <execution>
                      <id>default-cli</id>
                      <phase>verify</phase>
                      <goals>
                        <goal>scan</goal>
                        <goal>analyze</goal>
                      </goals>
                    </execution>
                  </executions>
                </plugin>
        """
      )
      .containing(
        """
              <plugin>
                <groupId>com.buschmais.jqassistant</groupId>
                <artifactId>jqassistant-maven-plugin</artifactId>
              </plugin>
        """
      )
      // Asciidoctor
      .containing("<jqassistant-asciidoctorj-extensions.version>")
      .containing(
        """
                <plugin>
                  <groupId>org.asciidoctor</groupId>
                  <artifactId>asciidoctor-maven-plugin</artifactId>
                  <version>${asciidoctor-maven-plugin.version}</version>
                  <executions>
                    <execution>
                      <id>html</id>
                      <phase>verify</phase>
                      <goals>
                        <goal>process-asciidoc</goal>
                      </goals>
                    </execution>
                  </executions>
                  <dependencies>
                    <dependency>
                      <groupId>org.jqassistant.tooling.asciidoctorj</groupId>
                      <artifactId>jqassistant-asciidoctorj-extensions</artifactId>
                      <version>${jqassistant-asciidoctorj-extensions.version}</version>
                    </dependency>
                  </dependencies>
                  <configuration>
                    <backend>html5</backend>
                    <attributes>
                      <jqassistant-report-path>${project.build.directory}/jqassistant/jqassistant-report.xml</jqassistant-report-path>
                    </attributes>
                  </configuration>
                </plugin>
        """
      )
      .containing(
        """
              <plugin>
                <groupId>org.asciidoctor</groupId>
                <artifactId>asciidoctor-maven-plugin</artifactId>
              </plugin>
        """
      );
  }

  @Test
  void shouldBuildJqassistantJmoleculesModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    JHipsterModule module = factory.buildJMoleculesModule(properties);

    assertThatModuleWithFiles(module, pomFile(), jQAssistantYmlFile())
      .hasFile(".jqassistant.yml")
      .containing(
        """
            - group-id: org.jqassistant.plugin
              artifact-id: jqassistant-jmolecules-plugin
              version: ${jqassistant-jmolecules-plugin.version}
            # jhipster-needle-jqassistant-plugin
        """
      )
      .containing(
        """
              - jmolecules-ddd:Default
              - jmolecules-hexagonal:Default
              - jmolecules-event:Default
              # jhipster-needle-jqassistant-analyze-group
        """
      )
      .and()
      .hasFile("pom.xml")
      // jQAssistant
      .containing("<jqassistant-jmolecules-plugin.version>");
  }

  @Test
  void shouldBuildJqassistantSpringModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    JHipsterModule module = factory.buildSpringModule(properties);

    assertThatModuleWithFiles(module, pomFile(), jQAssistantYmlFile())
      .hasFile(".jqassistant.yml")
      .containing(
        """
            - group-id: org.jqassistant.plugin
              artifact-id: jqassistant-spring-plugin
              version: ${jqassistant-spring-plugin.version}
            # jhipster-needle-jqassistant-plugin
        """
      )
      .containing(
        """
              - spring-boot:Default
              # jhipster-needle-jqassistant-analyze-group
        """
      )
      .and()
      .hasFile("pom.xml")
      // jQAssistant
      .containing("<jqassistant-spring-plugin.version>");
  }

  private JHipsterModulesAssertions.ModuleFile jQAssistantYmlFile() {
    return file("src/main/resources/generator/server/documentation/jqassistant/.jqassistant.yml", ".jqassistant.yml");
  }
}
