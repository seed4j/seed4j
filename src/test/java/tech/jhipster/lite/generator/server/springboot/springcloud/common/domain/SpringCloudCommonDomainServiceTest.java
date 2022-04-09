package tech.jhipster.lite.generator.server.springboot.springcloud.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProject;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.Base64Utils;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SpringCloudCommonDomainServiceTest {

  public static final String SOURCE_FOLDER_PATH = "server/springboot/springcloud/eureka/src";
  public static final String SOURCE_FILE_NAME = "bootstrap.properties";
  public static final String DESTINATION_FILE_FOLDER = "src/main/resources/config";

  @Mock
  ProjectRepository projectRepository;

  @Mock
  BuildToolService buildToolService;

  @Mock
  DockerService dockerService;

  @InjectMocks
  SpringCloudCommonDomainService springCloudCommonDomainService;

  private Dependency getExpectedManagementDependency() {
    return Dependency
      .builder()
      .groupId("org.springframework.cloud")
      .artifactId("spring-cloud-dependencies")
      .version("\\${spring-cloud.version}")
      .type("pom")
      .scope("import")
      .build();
  }

  private Dependency getExpectedStarterBootstrapDependency() {
    return Dependency.builder().groupId("org.springframework.cloud").artifactId("spring-cloud-starter-bootstrap").build();
  }

  @Nested
  class AddSpringCloudCommonDependenciesTest {

    @Test
    void shouldAddSpringCloudCommonDependencies() {
      // Given
      Project project = tmpProject();
      when(buildToolService.getVersion(project, "spring-cloud")).thenReturn(Optional.of("0.0.0"));

      // When
      springCloudCommonDomainService.addSpringCloudCommonDependencies(project);

      // Then
      verify(buildToolService).addProperty(project, "spring-cloud.version", "0.0.0");

      ArgumentCaptor<Dependency> dependencyArgCaptor = ArgumentCaptor.forClass(Dependency.class);
      verify(buildToolService).addDependencyManagement(eq(project), dependencyArgCaptor.capture());
      assertThat(dependencyArgCaptor.getValue()).usingRecursiveComparison().isEqualTo(getExpectedManagementDependency());

      verify(buildToolService).addDependency(eq(project), dependencyArgCaptor.capture());
      assertThat(dependencyArgCaptor.getValue()).usingRecursiveComparison().isEqualTo(getExpectedStarterBootstrapDependency());
    }

    @Test
    void shouldThrowExceptionWhenSpringCloudDependencyVersionNotFound() {
      // Given
      Project project = tmpProject();

      when(buildToolService.getVersion(project, "spring-cloud")).thenReturn(Optional.empty());

      // When + Then
      assertThatThrownBy(() -> springCloudCommonDomainService.addSpringCloudCommonDependencies(project))
        .isExactlyInstanceOf(GeneratorException.class);
    }
  }

  @Nested
  class AddJhipsterRegistryDockerComposeTest {

    @Test
    void shouldAddJhipsterRegistryDockerCompose() {
      // Given
      Project project = tmpProject();

      when(dockerService.getImageNameWithVersion("jhipster/jhipster-registry")).thenReturn(Optional.of("jhipster/jhipster-registry:1.1.1"));

      String expectedBase64Secret = "encodedSecret";

      try (MockedStatic<Base64Utils> base64Utils = mockStatic(Base64Utils.class)) {
        base64Utils.when(Base64Utils::getBase64Secret).thenReturn(expectedBase64Secret);

        // When
        springCloudCommonDomainService.addJhipsterRegistryDockerCompose(project);

        // Then
        assertThat(project.getConfig("jhipsterRegistryDockerImage")).contains("jhipster/jhipster-registry:1.1.1");
        assertThat(project.getConfig("base64JwtSecret")).contains(expectedBase64Secret);

        verify(projectRepository, times(2)).template(any(Project.class), anyString(), anyString(), anyString(), anyString());
        verify(projectRepository)
          .template(
            project,
            "server/springboot/springcloud/configclient",
            "jhipster-registry.yml",
            "src/main/docker",
            "jhipster-registry.yml"
          );

        verify(projectRepository)
          .template(
            project,
            "server/springboot/springcloud/configclient",
            "application.config.properties",
            "src/main/docker/central-server-config/localhost-config",
            "application.properties"
          );
      }
    }

    @Test
    void shouldThrowExceptionWhenImageVersionNotFound() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springCloudCommonDomainService.addJhipsterRegistryDockerCompose(project))
        .isInstanceOf(GeneratorException.class)
        .hasMessageContaining("jhipster/jhipster-registry");
    }
  }

  @Nested
  class AddOrMergeBootstrapPropertiesTest {

    @Test
    void shouldCreatePropertyFiles() {
      // Given
      String folderPath = "/project/folder/path";
      Project project = new Project.ProjectBuilder().folder(folderPath).build();

      try (MockedStatic<FileUtils> fileUtils = mockStatic(FileUtils.class)) {
        String destinationFilePath = "/destination/file/path/bootstrap.properties";
        fileUtils.when(() -> FileUtils.getPath(folderPath, DESTINATION_FILE_FOLDER, SOURCE_FILE_NAME)).thenReturn(destinationFilePath);
        fileUtils.when(() -> FileUtils.exists(anyString())).thenReturn(false);

        // When
        springCloudCommonDomainService.addOrMergeBootstrapProperties(
          project,
          SOURCE_FOLDER_PATH,
          SOURCE_FILE_NAME,
          DESTINATION_FILE_FOLDER
        );

        // Then
        fileUtils.verify(() -> FileUtils.exists(destinationFilePath));
        fileUtils.verify(() -> FileUtils.getPath(folderPath, DESTINATION_FILE_FOLDER, SOURCE_FILE_NAME));
        verify(projectRepository).template(project, SOURCE_FOLDER_PATH, SOURCE_FILE_NAME, DESTINATION_FILE_FOLDER);
      }
    }

    @Test
    void shouldAppendPropertiesInExistingFile() {
      // Given
      String folderPath = "/project/folder/path";
      Map<String, Object> config = Map.of("baseName", "foo");
      Project project = new Project.ProjectBuilder().folder(folderPath).config(config).build();

      try (MockedStatic<FileUtils> fileUtils = mockStatic(FileUtils.class)) {
        String destinationFilePath = "/destination/file/path/bootstrap.properties";
        fileUtils.when(() -> FileUtils.getPath(folderPath, DESTINATION_FILE_FOLDER, SOURCE_FILE_NAME)).thenReturn(destinationFilePath);

        fileUtils.when(() -> FileUtils.exists(anyString())).thenReturn(true);

        when(projectRepository.getComputedTemplate(project, SOURCE_FOLDER_PATH, SOURCE_FILE_NAME))
          .thenReturn(
            """
              # a comment
              prop1=valueFromGenerator1

              prop2=valueFromGenerator2
              prop3=valueFromGenerator3
              """
          );

        fileUtils
          .when(() -> FileUtils.read(destinationFilePath))
          .thenReturn(
            """
              # another comment
              prop1=existingValueEditedByUser
              prop2=value2
              prop4=valueAddedByUser

              """
          );

        // When
        springCloudCommonDomainService.addOrMergeBootstrapProperties(
          project,
          SOURCE_FOLDER_PATH,
          SOURCE_FILE_NAME,
          DESTINATION_FILE_FOLDER
        );

        // Then
        fileUtils.verify(() -> FileUtils.appendLines(destinationFilePath, List.of("", "prop3=valueFromGenerator3")));
      }
    }

    @Test
    void shouldNotAppendPropertiesInExistingFileWhenAllPropertiesExist() {
      // Given
      String folderPath = "/project/folder/path";
      Map<String, Object> config = Map.of("baseName", "foo");
      Project project = new Project.ProjectBuilder().folder(folderPath).config(config).build();

      try (MockedStatic<FileUtils> fileUtils = mockStatic(FileUtils.class)) {
        String destinationFilePath = "/destination/file/path/bootstrap.properties";
        fileUtils.when(() -> FileUtils.getPath(folderPath, DESTINATION_FILE_FOLDER, SOURCE_FILE_NAME)).thenReturn(destinationFilePath);

        fileUtils.when(() -> FileUtils.exists(anyString())).thenReturn(true);

        when(projectRepository.getComputedTemplate(project, SOURCE_FOLDER_PATH, SOURCE_FILE_NAME))
          .thenReturn("""
            # a comment
            prop1=valueFromGenerator1
            """);

        fileUtils
          .when(() -> FileUtils.read(destinationFilePath))
          .thenReturn("""
            # a comment
            prop1=valueFromGenerator1
            """);

        // When
        springCloudCommonDomainService.addOrMergeBootstrapProperties(
          project,
          SOURCE_FOLDER_PATH,
          SOURCE_FILE_NAME,
          DESTINATION_FILE_FOLDER
        );

        // Then
        fileUtils.verify(() -> FileUtils.appendLines(anyString(), any()), never());
      }
    }

    @Test
    void shouldThrowExceptionWhenDestinationFileCannotBeRead() {
      String folderPath = "/project/folder/path";
      Map<String, Object> config = Map.of("baseName", "foo");
      Project project = new Project.ProjectBuilder().folder(folderPath).config(config).build();

      try (MockedStatic<FileUtils> fileUtils = mockStatic(FileUtils.class)) {
        String destinationFilePath = "/destination/file/path/bootstrap.properties";
        fileUtils.when(() -> FileUtils.getPath(folderPath, DESTINATION_FILE_FOLDER, SOURCE_FILE_NAME)).thenReturn(destinationFilePath);

        fileUtils.when(() -> FileUtils.exists(anyString())).thenReturn(true);

        when(projectRepository.getComputedTemplate(project, SOURCE_FOLDER_PATH, SOURCE_FILE_NAME)).thenReturn("content");

        fileUtils.when(() -> FileUtils.read(destinationFilePath)).thenThrow(IOException.class);

        // When + Then
        assertThatThrownBy(() ->
            springCloudCommonDomainService.addOrMergeBootstrapProperties(
              project,
              SOURCE_FOLDER_PATH,
              SOURCE_FILE_NAME,
              DESTINATION_FILE_FOLDER
            )
          )
          .isInstanceOf(GeneratorException.class)
          .hasMessageContaining(destinationFilePath);

        fileUtils.verify(() -> FileUtils.appendLines(any(), any()), never());
      }
    }

    @Test
    void shouldThrowExceptionWhenAppendLinesInDestinationFileFailed() {
      String folderPath = "/project/folder/path";
      Map<String, Object> config = Map.of("baseName", "foo");
      Project project = new Project.ProjectBuilder().folder(folderPath).config(config).build();

      try (MockedStatic<FileUtils> fileUtils = mockStatic(FileUtils.class)) {
        String destinationFilePath = "/destination/file/path/bootstrap.properties";
        fileUtils.when(() -> FileUtils.getPath(folderPath, DESTINATION_FILE_FOLDER, SOURCE_FILE_NAME)).thenReturn(destinationFilePath);

        fileUtils.when(() -> FileUtils.exists(anyString())).thenReturn(true);

        when(projectRepository.getComputedTemplate(project, SOURCE_FOLDER_PATH, SOURCE_FILE_NAME)).thenReturn("prop1=true");

        fileUtils.when(() -> FileUtils.read(destinationFilePath)).thenReturn("");

        fileUtils.when(() -> FileUtils.appendLines(anyString(), any())).thenThrow(IOException.class);

        // When + Then
        assertThatThrownBy(() ->
            springCloudCommonDomainService.addOrMergeBootstrapProperties(
              project,
              SOURCE_FOLDER_PATH,
              SOURCE_FILE_NAME,
              DESTINATION_FILE_FOLDER
            )
          )
          .isInstanceOf(GeneratorException.class)
          .hasMessageContaining(destinationFilePath);
      }
    }
  }
}
