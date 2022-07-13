package tech.jhipster.lite.generator.server.sonar.domain;

import static org.mockito.Mockito.*;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.docker.domain.DockerImage;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SonarModulesFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private SonarModulesFactory factory;

  @Test
  void shouldBuildBackendModule() {
    mockSonarqubeImage();

    JHipsterModule module = factory.buildBackendModule(properties());

    assertCommonModule(module)
      .hasFile("sonar-project.properties")
      .containing(
        "sonar.exclusions=src/main/webapp/main.ts, src/main/webapp/app/main.ts, src/main/webapp/content/**/*.*, src/main/webapp/i18n/*.js, target/classes/static/**/*.*, src/main/webapp/app/index.tsx"
      )
      .notContaining("sonar.testExecutionReportPaths=target/test-results/jest/TESTS-results-sonar.xml");
  }

  @Test
  void shouldBuildBackendFrontendModule() {
    mockSonarqubeImage();

    JHipsterModule module = factory.buildBackendFrontendModule(properties());

    assertCommonModule(module)
      .hasFile("sonar-project.properties")
      .containing(
        "sonar.exclusions=src/main/webapp/main.ts, src/main/webapp/app/main.ts, src/main/webapp/content/**/*.*, src/main/webapp/i18n/*.js, target/classes/static/**/*.*, src/main/webapp/app/index.tsx, src/main/webapp/routes/index.svelte"
      )
      .containing("sonar.testExecutionReportPaths=target/test-results/jest/TESTS-results-sonar.xml");
  }

  private void mockSonarqubeImage() {
    when(dockerImages.get("sonarqube")).thenReturn(new DockerImage("sonarqube", "1.1.1"));
  }

  private JHipsterModuleProperties properties() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();
    return properties;
  }

  private ModuleAsserter assertCommonModule(JHipsterModule module) {
    return assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
                  <plugin>
                    <groupId>org.codehaus.mojo</groupId>
                    <artifactId>properties-maven-plugin</artifactId>
                    <version>${properties-maven-plugin.version}</version>
                    <executions>
                      <execution>
                        <phase>initialize</phase>
                        <goals>
                          <goal>read-project-properties</goal>
                        </goals>
                        <configuration>
                          <files>
                            <file>sonar-project.properties</file>
                          </files>
                        </configuration>
                      </execution>
                    </executions>
                  </plugin>
            """
      )
      .containing(
        """
                    <plugin>
                      <groupId>org.sonarsource.scanner.maven</groupId>
                      <artifactId>sonar-maven-plugin</artifactId>
                      <version>${sonar-maven-plugin.version}</version>
                    </plugin>
            """
      )
      .and()
      .hasFile("src/main/docker/sonar.yml")
      .containing("sonarqube:1.1.1")
      .and();
  }
}
