package tech.jhipster.lite.generator.server.springboot.springcloud.common.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SpringCloudCommonDomainServiceTest {

  public static final String SOURCE_FOLDER_PATH = "server/springboot/springcloud/eureka/src";
  public static final String SOURCE_FILE_NAME = "bootstrap.properties";
  public static final String DESTINATION_FILE_FOLDER = "src/main/resources/config";

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  SpringCloudCommonDomainService springCloudCommonDomainService;

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
