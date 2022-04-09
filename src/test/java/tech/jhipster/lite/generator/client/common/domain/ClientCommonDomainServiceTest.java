package tech.jhipster.lite.generator.client.common.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.common.domain.JsonUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class ClientCommonDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  ClientCommonDomainService clientCommonDomainService;

  @Nested
  class ExcludeInTsconfigTest {

    @Test
    void shouldExcludeInTsconfigJson() {
      // Given
      String projectFolderPath = "/path/to/project";
      Project project = Project.builder().folder(projectFolderPath).build();

      try (MockedStatic<FileUtils> fileUtils = mockStatic(FileUtils.class)) {
        String expectedTsConfigJsonPath = "/path/to/project/tsconfig.json";
        fileUtils.when(() -> FileUtils.getPath(projectFolderPath, "tsconfig.json")).thenReturn(expectedTsConfigJsonPath);
        String tsConfigContent = """
          {
            "exclude": ["value1"]
          }
          """;
        fileUtils.when(() -> FileUtils.read(expectedTsConfigJsonPath)).thenReturn(tsConfigContent);

        try (MockedStatic<JsonUtils> jsonUtils = mockStatic(JsonUtils.class)) {
          String expectedTsConfigContent = """
            {
              "exclude": ["value1", "value2"]
            }
            """;
          jsonUtils.when(() -> JsonUtils.addValueInArray("exclude", "value2", tsConfigContent)).thenReturn(expectedTsConfigContent);

          // When
          clientCommonDomainService.excludeInTsconfigJson(project, "value2");

          // Then
          verify(projectRepository).write(project, expectedTsConfigContent, ".", "tsconfig.json");
        }
      }
    }

    @Test
    void shouldThrowExceptionWhenFileCannotBeRead() {
      String projectFolderPath = "/path/to/project";
      Project project = Project.builder().folder(projectFolderPath).build();

      try (MockedStatic<FileUtils> fileUtils = mockStatic(FileUtils.class)) {
        String expectedTsConfigJsonPath = "/path/to/project/tsconfig.json";
        fileUtils.when(() -> FileUtils.getPath(projectFolderPath, "tsconfig.json")).thenReturn(expectedTsConfigJsonPath);
        fileUtils.when(() -> FileUtils.read(expectedTsConfigJsonPath)).thenThrow(IOException.class);

        // When + Then
        assertThatThrownBy(() -> clientCommonDomainService.excludeInTsconfigJson(project, "value2")).isInstanceOf(GeneratorException.class);

        verify(projectRepository, never()).write(any(Project.class), anyString(), anyString(), anyString());
      }
    }
  }
}
