package tech.jhipster.lite.generator.server.sonar.domain;

import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PROJECT_NAME;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

public class SonarDomainService implements SonarService {

  public static final String SOURCE = "server/sonar";

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final DockerService dockerService;

  public SonarDomainService(ProjectRepository projectRepository, BuildToolService buildToolService, DockerService dockerService) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.dockerService = dockerService;
  }

  @Override
  public void addSonarJavaBackend(Project project) {
    addPropertiesPlugin(project);
    addSonarScannerPluginManagement(project);
    addPropertiesFile(project);
    addDockerCompose(project);
  }

  @Override
  public void addSonarJavaBackendAndFrontend(Project project) {
    addPropertiesPlugin(project);
    addSonarScannerPluginManagement(project);
    addFullstackPropertiesFile(project);
    addDockerCompose(project);
  }

  private void addPropertiesPlugin(Project project) {
    Plugin plugin = Plugin
      .builder()
      .groupId("org.codehaus.mojo")
      .artifactId("properties-maven-plugin")
      .version("\\${properties-maven-plugin.version}")
      .additionalElements(
        """
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
        </executions>"""
      )
      .build();
    buildToolService
      .getVersion(project, "properties-maven-plugin")
      .ifPresentOrElse(
        version -> buildToolService.addProperty(project, "properties-maven-plugin.version", version),
        () -> {
          throw new GeneratorException("Version not found: properties-maven-plugin");
        }
      );
    buildToolService.addPlugin(project, plugin);
  }

  private void addSonarScannerPluginManagement(Project project) {
    Plugin plugin = Plugin
      .builder()
      .groupId("org.sonarsource.scanner.maven")
      .artifactId("sonar-maven-plugin")
      .version("\\${sonar-maven-plugin.version}")
      .build();
    buildToolService
      .getVersion(project, "sonar-maven-plugin")
      .ifPresentOrElse(
        version -> buildToolService.addProperty(project, "sonar-maven-plugin.version", version),
        () -> {
          throw new GeneratorException("Version not found: sonar-maven-plugin");
        }
      );
    buildToolService.addPluginManagement(project, plugin);
  }

  private void addPropertiesFile(Project project) {
    project.addDefaultConfig(BASE_NAME);
    project.addDefaultConfig(PROJECT_NAME);
    projectRepository.template(project, SOURCE, "sonar-project.properties");
  }

  private void addFullstackPropertiesFile(Project project) {
    project.addDefaultConfig(BASE_NAME);
    project.addDefaultConfig(PROJECT_NAME);
    projectRepository.template(project, SOURCE, "sonar-fullstack-project.properties", "", "sonar-project.properties");
  }

  private void addDockerCompose(Project project) {
    project.addDefaultConfig(BASE_NAME);
    dockerService
      .getImageNameWithVersion(Sonar.getSonarqubeDockerImageName())
      .ifPresentOrElse(
        imageName -> project.addConfig("sonarqubeDockerImage", imageName),
        () -> {
          throw new GeneratorException("Version not found for docker image: " + Sonar.getSonarqubeDockerImageName());
        }
      );
    projectRepository.template(project, SOURCE, "sonar.yml", "src/main/docker", "sonar.yml");
  }
}
