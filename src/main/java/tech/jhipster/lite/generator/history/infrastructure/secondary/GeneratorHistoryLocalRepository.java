package tech.jhipster.lite.generator.history.infrastructure.secondary;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.common.domain.JsonUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.history.domain.GeneratorHistoryData;
import tech.jhipster.lite.generator.history.domain.GeneratorHistoryRepository;
import tech.jhipster.lite.generator.history.domain.GeneratorHistoryValue;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@Repository
public record GeneratorHistoryLocalRepository(ProjectRepository projectRepository) implements GeneratorHistoryRepository {
  private static final String HISTORY_FILE_NAME = "history.json";
  private static final String HISTORY_FILE_FOLDER_SOURCE = "history";
  private static final String HISTORY_FOLDER_PATH_DEST = ".jhipster";

  @Override
  public GeneratorHistoryData getHistoryData(Project project) {
    String filePath = getHistoryFilePath(project);
    String historyFileContent;
    try {
      historyFileContent = FileUtils.read(filePath);
    } catch (IOException e) {
      throw new GeneratorException("Error on file " + filePath + " : " + e.getMessage());
    }
    return deserializeHistoryFile(filePath, historyFileContent);
  }

  @Override
  public void addHistoryValue(Project project, GeneratorHistoryValue generatorHistoryValue) {
    String filePath = getHistoryFilePath(project);
    GeneratorHistoryData generatorHistoryData;
    try {
      String historyFileContent = FileUtils.read(filePath);
      generatorHistoryData = deserializeHistoryFile(filePath, historyFileContent);
    } catch (IOException e) {
      // The file does not exist
      createHistoryFile(project);
      generatorHistoryData = getHistoryData(project);
    }

    generatorHistoryData.getValues().add(generatorHistoryValue);
    String newHistoryFileContent;
    try {
      newHistoryFileContent = JsonUtils.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(generatorHistoryData);
    } catch (JsonProcessingException e) {
      throw new GeneratorException("Cannot serialize the history data " + generatorHistoryData + " : " + e.getMessage());
    }
    try {
      FileUtils.write(filePath, newHistoryFileContent, project.getEndOfLine());
    } catch (IOException e) {
      throw new GeneratorException("Cannot write JSON content in file " + filePath + " : " + e.getMessage());
    }
  }

  private void createHistoryFile(Project project) {
    projectRepository.add(project, HISTORY_FILE_FOLDER_SOURCE, HISTORY_FILE_NAME, HISTORY_FOLDER_PATH_DEST, HISTORY_FILE_NAME);
  }

  private String getHistoryFilePath(Project project) {
    return FileUtils.getPath(project.getFolder(), HISTORY_FOLDER_PATH_DEST, HISTORY_FILE_NAME);
  }

  private GeneratorHistoryData deserializeHistoryFile(String filePath, String historyFileContent) {
    try {
      return JsonUtils.getObjectMapper().readValue(historyFileContent, GeneratorHistoryData.class);
    } catch (JsonProcessingException e) {
      throw new GeneratorException("Cannot deserialize JSON of file " + filePath + " : " + e.getMessage());
    }
  }
}
