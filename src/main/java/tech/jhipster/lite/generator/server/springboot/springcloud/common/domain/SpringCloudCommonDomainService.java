package tech.jhipster.lite.generator.server.springboot.springcloud.common.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommon.JHIPSTER_REGISTRY_DOCKER_IMAGE_NAME;
import static tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommon.springCloudDependencyManagement;
import static tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommon.springCloudStarterBootstrap;
import static tech.jhipster.lite.generator.tools.domain.Constants.COMMENT_PROPERTIES_PREFIX;
import static tech.jhipster.lite.generator.tools.domain.Constants.KEY_VALUE_PROPERTIES_SEPARATOR;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;

import java.io.IOException;
import java.util.List;
import tech.jhipster.lite.common.domain.Base64Utils;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

public class SpringCloudCommonDomainService implements SpringCloudCommonService {

  public static final String SPRING_CLOUD_SOURCE = "server/springboot/springcloud/configclient";
  public static final String JHIPSTER_REGISTRY_YML_FILE_NAME = "jhipster-registry.yml";

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final DockerService dockerService;

  public SpringCloudCommonDomainService(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    DockerService dockerService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.dockerService = dockerService;
  }

  @Override
  public void addSpringCloudCommonDependencies(Project project) {
    String springCloudVersion =
      this.buildToolService.getVersion(project, "spring-cloud").orElseThrow(() -> new GeneratorException("Spring Cloud version not found"));

    buildToolService.addProperty(project, "spring-cloud.version", springCloudVersion);
    buildToolService.addDependencyManagement(project, springCloudDependencyManagement());
    buildToolService.addDependency(project, springCloudStarterBootstrap());
  }

  @Override
  public void addJhipsterRegistryDockerCompose(Project project) {
    project.addDefaultConfig(BASE_NAME);

    dockerService
      .getImageNameWithVersion(JHIPSTER_REGISTRY_DOCKER_IMAGE_NAME)
      .ifPresentOrElse(
        imageName -> project.addConfig("jhipsterRegistryDockerImage", imageName),
        () -> {
          throw new GeneratorException("Version not found for docker image: " + JHIPSTER_REGISTRY_DOCKER_IMAGE_NAME);
        }
      );

    project.addConfig("base64JwtSecret", Base64Utils.getBase64Secret());

    projectRepository.template(
      project,
      SPRING_CLOUD_SOURCE,
      JHIPSTER_REGISTRY_YML_FILE_NAME,
      "src/main/docker",
      JHIPSTER_REGISTRY_YML_FILE_NAME
    );
    projectRepository.template(
      project,
      SPRING_CLOUD_SOURCE,
      "application.config.properties",
      "src/main/docker/central-server-config/localhost-config",
      "application.properties"
    );
  }

  @Override
  public void addOrMergeBootstrapProperties(Project project, String sourceFolderPath, String sourceFileName, String destinationFileFolder) {
    String destinationFilePath = getPath(project.getFolder(), destinationFileFolder, sourceFileName);
    if (!FileUtils.exists(destinationFilePath)) {
      createFile(project, sourceFolderPath, sourceFileName, destinationFileFolder);
    } else {
      mergeContentFile(project, sourceFolderPath, sourceFileName, destinationFilePath);
    }
  }

  private void createFile(Project project, String sourceFolderPath, String sourceFileName, String destinationFileFolder) {
    projectRepository.template(project, sourceFolderPath, sourceFileName, destinationFileFolder);
  }

  private void mergeContentFile(Project project, String sourceFolderPath, String sourceFileName, String destinationFilePath) {
    List<String> sourceFileLines = projectRepository.getComputedTemplate(project, sourceFolderPath, sourceFileName).lines().toList();

    List<String> destinationFileLines;
    try {
      destinationFileLines = FileUtils.read(destinationFilePath).lines().toList();
    } catch (IOException e) {
      throw new GeneratorException("Cannot get content file: " + destinationFilePath, e);
    }

    List<String> destinationFilePropertyKeys = getPropertyKeys(destinationFileLines);

    List<String> linesToAdd = sourceFileLines
      .stream()
      .filter(line -> !line.trim().startsWith(COMMENT_PROPERTIES_PREFIX))
      .filter(line -> {
        String propertyKey = getPropertyKey(line);
        return !destinationFilePropertyKeys.contains(propertyKey);
      })
      .toList();
    if (!linesToAdd.isEmpty()) {
      try {
        FileUtils.appendLines(destinationFilePath, linesToAdd);
      } catch (IOException e) {
        throw new GeneratorException("Cannot get append lines in file: " + destinationFilePath, e);
      }
    }
  }

  private List<String> getPropertyKeys(List<String> fileContentLines) {
    return fileContentLines
      .stream()
      .filter(line -> !line.startsWith(COMMENT_PROPERTIES_PREFIX))
      .filter(line -> !line.trim().isEmpty())
      .map(this::getPropertyKey)
      .toList();
  }

  private String getPropertyKey(String propertyLine) {
    return propertyLine.split(KEY_VALUE_PROPERTIES_SEPARATOR, 2)[0];
  }
}
