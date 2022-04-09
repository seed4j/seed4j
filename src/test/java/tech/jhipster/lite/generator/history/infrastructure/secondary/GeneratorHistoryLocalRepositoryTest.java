package tech.jhipster.lite.generator.history.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.common.domain.JsonUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.history.domain.GeneratorHistoryData;
import tech.jhipster.lite.generator.history.domain.GeneratorHistoryValue;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class GeneratorHistoryLocalRepositoryTest {

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  GeneratorHistoryLocalRepository generatorHistoryLocalRepository;

  @Test
  void shouldGetHistoryData() {
    // Given
    Project project = tmpProject();

    try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
      String historyFilePath = "/path/history.json";
      fileUtils.when(() -> FileUtils.getPath(project.getFolder(), ".jhipster", "history.json")).thenReturn(historyFilePath);
      fileUtils.when(() -> FileUtils.read(historyFilePath)).thenReturn(getFileContent());

      // When
      GeneratorHistoryData generatorHistoryData = generatorHistoryLocalRepository.getHistoryData(project);

      // Then
      GeneratorHistoryData expectedGeneratorHistoryData = new GeneratorHistoryData();
      expectedGeneratorHistoryData.getValues().add(new GeneratorHistoryValue("springboot-init"));
      assertThat(generatorHistoryData).usingRecursiveComparison().isEqualTo(expectedGeneratorHistoryData);
    }
  }

  @Test
  void shouldNotGetHistoryDataWhenFileDoesNotExist() {
    // Given
    Project project = tmpProject();

    try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
      fileUtils.when(() -> FileUtils.getPath(any())).thenReturn("/path/history.json");
      fileUtils.when(() -> FileUtils.read(anyString())).thenThrow(new IOException());

      // When + Then
      assertThatThrownBy(() -> generatorHistoryLocalRepository.getHistoryData(project)).isExactlyInstanceOf(GeneratorException.class);
    }
  }

  @Test
  void shouldNotGetHistoryDataWhenJsonIsNotDeserializable() {
    // Given
    Project project = tmpProject();

    try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
      fileUtils.when(() -> FileUtils.getPath(any())).thenReturn("/path/history.json");
      fileUtils.when(() -> FileUtils.read(anyString())).thenReturn("invalidJsonContent");

      // When + Then
      assertThatThrownBy(() -> generatorHistoryLocalRepository.getHistoryData(project)).isExactlyInstanceOf(GeneratorException.class);
    }
  }

  @Test
  void shouldAddHistoryValueInExistingFile() {
    // Given
    Project project = tmpProject();

    try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
      String historyFilePath = "/path/history.json";
      fileUtils.when(() -> FileUtils.getPath(project.getFolder(), ".jhipster", "history.json")).thenReturn(historyFilePath);
      fileUtils.when(() -> FileUtils.read(historyFilePath)).thenReturn(getFileContent());

      // When
      GeneratorHistoryValue generatorHistoryValue = new GeneratorHistoryValue("tomcat");
      generatorHistoryLocalRepository.addHistoryValue(project, generatorHistoryValue);

      // Then
      fileUtils.verify(() -> FileUtils.read(anyString()), times(1));
      fileUtils.verify(() -> {
        ArgumentCaptor<String> fileContentArgCaptor = ArgumentCaptor.forClass(String.class);
        try {
          FileUtils.write(eq(historyFilePath), fileContentArgCaptor.capture(), eq(project.getEndOfLine()));
        } catch (IOException e) {
          fail("Unexpected IOException");
        }
        List<String> fileContentLines = fileContentArgCaptor.getValue().lines().collect(Collectors.toList());
        assertThat(fileContentLines).isEqualTo(getExpectedFileContentLines());
      });

      verifyNoInteractions(projectRepository);
    }
  }

  @Test
  void shouldAddHistoryValueInNewFile() {
    // Given
    Project project = tmpProject();

    try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
      String historyFilePath = "/path/history.json";
      fileUtils.when(() -> FileUtils.getPath(project.getFolder(), ".jhipster", "history.json")).thenReturn(historyFilePath);
      fileUtils.when(() -> FileUtils.read(historyFilePath)).thenThrow(new IOException()).thenReturn(getFileContent());

      // When
      GeneratorHistoryValue generatorHistoryValue = new GeneratorHistoryValue("tomcat");
      generatorHistoryLocalRepository.addHistoryValue(project, generatorHistoryValue);

      // Then
      verify(projectRepository).add(project, "history", "history.json", ".jhipster", "history.json");

      fileUtils.verify(() -> FileUtils.read(anyString()), times(2));
      fileUtils.verify(() -> {
        ArgumentCaptor<String> fileContentArgCaptor = ArgumentCaptor.forClass(String.class);
        try {
          FileUtils.write(eq(historyFilePath), fileContentArgCaptor.capture(), eq(project.getEndOfLine()));
        } catch (IOException e) {
          fail("Unexpected IOException");
        }
        List<String> fileContentLines = fileContentArgCaptor.getValue().lines().collect(Collectors.toList());
        assertThat(fileContentLines).isEqualTo(getExpectedFileContentLines());
      });
    }
  }

  @Test
  void shouldNotAddHistoryValueWhenJsonSerializationError() throws JsonProcessingException {
    // Given
    Project project = tmpProject();

    ObjectMapper spiedObjectMapper = spy(JsonUtils.getObjectMapper());
    ObjectWriter spiedObjectWriter = spy(spiedObjectMapper.writer());
    when(spiedObjectMapper.writerWithDefaultPrettyPrinter()).thenReturn(spiedObjectWriter);
    when(spiedObjectWriter.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

    try (MockedStatic<JsonUtils> jsonUtils = Mockito.mockStatic(JsonUtils.class)) {
      jsonUtils.when(JsonUtils::getObjectMapper).thenReturn(spiedObjectMapper);

      try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
        String historyFilePath = "/path/history.json";
        fileUtils.when(() -> FileUtils.getPath(project.getFolder(), ".jhipster", "history.json")).thenReturn(historyFilePath);
        fileUtils.when(() -> FileUtils.read(historyFilePath)).thenReturn(getFileContent());

        GeneratorHistoryValue generatorHistoryValue = new GeneratorHistoryValue("tomcat");

        // When + Then
        assertThatThrownBy(() -> generatorHistoryLocalRepository.addHistoryValue(project, generatorHistoryValue))
          .isExactlyInstanceOf(GeneratorException.class);

        fileUtils.verify(
          () -> {
            try {
              FileUtils.write(anyString(), anyString(), anyString());
            } catch (IOException e) {
              fail("Unexpected IOException");
            }
          },
          never()
        );

        verifyNoInteractions(projectRepository);
      }
    }
  }

  @Test
  void shouldNotAddHistoryValueWheIoExceptionDuringWriting() {
    // Given
    Project project = tmpProject();

    try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
      String historyFilePath = "/path/history.json";
      fileUtils.when(() -> FileUtils.getPath(project.getFolder(), ".jhipster", "history.json")).thenReturn(historyFilePath);
      fileUtils.when(() -> FileUtils.read(historyFilePath)).thenReturn(getFileContent());
      fileUtils.when(() -> FileUtils.write(anyString(), anyString(), anyString())).thenThrow(new IOException());

      // When
      GeneratorHistoryValue generatorHistoryValue = new GeneratorHistoryValue("tomcat");
      assertThatThrownBy(() -> generatorHistoryLocalRepository.addHistoryValue(project, generatorHistoryValue))
        .isInstanceOf(GeneratorException.class);
    }
  }

  private String getFileContent() {
    return """
        {
          "values": [
            { "serviceId": "springboot-init"}
          ]
        }
      """;
  }

  private List<String> getExpectedFileContentLines() {
    return """
      {
        "values" : [ {
          "serviceId" : "springboot-init"
        }, {
          "serviceId" : "tomcat"
        } ]
      }""".lines()
      .collect(Collectors.toList());
  }
}
