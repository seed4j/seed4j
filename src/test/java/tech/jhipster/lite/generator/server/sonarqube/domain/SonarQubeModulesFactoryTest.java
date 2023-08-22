package tech.jhipster.lite.generator.server.sonarqube.domain;

import static org.mockito.Mockito.*;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.docker.DockerImageVersion;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SonarQubeModulesFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private SonarQubeModulesFactory factory;

  @Test
  void shouldBuildBackendModule() {
    mockSonarqubeImage();

    JHipsterModule module = factory.buildBackendModule(properties());

    assertCommonModule(module)
      .hasFile("sonar-project.properties")
      .containing(
        "sonar.exclusions=src/main/webapp/main.ts, src/main/webapp/app/main.ts, src/main/webapp/content/**/*.*, src/main/webapp/i18n/*.js, target/classes/static/**/*.*, src/main/webapp/app/index.tsx"
      )
      .notContaining("sonar.testExecutionReportPaths=target/test-results/TESTS-results-sonar.xml");
  }

  @Test
  void shouldBuildBackendFrontendModule() {
    mockSonarqubeImage();

    JHipsterModule module = factory.buildBackendFrontendModule(properties());

    assertCommonModule(module)
      .hasFile("sonar-project.properties")
      .containing(
        "sonar.exclusions=src/main/webapp/main.ts, src/main/webapp/app/main.ts, src/main/webapp/content/**/*.*, src/main/webapp/i18n/*.js, target/classes/static/**/*.*, src/main/webapp/app/index.tsx, src/main/webapp/routes/+page.svelte"
      )
      .containing("sonar.testExecutionReportPaths=target/test-results/TESTS-results-sonar.xml");
  }

  private void mockSonarqubeImage() {
    when(dockerImages.get("sonarqube")).thenReturn(new DockerImageVersion("sonarqube", "1.1.1"));
  }

  private JHipsterModuleProperties properties() {
    return JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();
  }

  private JHipsterModuleAsserter assertCommonModule(JHipsterModule module) {
    return assertThatModuleWithFiles(module, pomFile(), readmeFile())
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
      .and()
      .hasFile("documentation/sonar.md")
      .containing("docker compose -f src/main/docker/sonar.yml up -d")
      .and()
      .hasFile("README.md")
      .containing("docker compose -f src/main/docker/sonar.yml up -d")
      .containing("./mvnw clean verify sonar:sonar")
      .and();
  }
}
