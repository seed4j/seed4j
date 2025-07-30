package com.seed4j.project.infrastructure.secondary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.seed4j.project.domain.ProjectPath;
import com.seed4j.project.domain.ProjectsRepository;
import com.seed4j.project.domain.download.Project;
import com.seed4j.project.domain.history.ProjectHistory;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.error.domain.GeneratorException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class FileSystemProjectsRepository implements ProjectsRepository {

  private static final String PATH_PARAMETER = "path";

  private static final String HISTORY_FOLDER = ".jhipster/modules";
  private static final String HISTORY_FILE = "history.json";

  private final ObjectMapper json;
  private final ObjectWriter writer;
  private final FileSystemProjectDownloader downloader;

  public FileSystemProjectsRepository(ObjectMapper json) {
    this.json = json;

    writer = json.writerWithDefaultPrettyPrinter();
    downloader = new FileSystemProjectDownloader();
  }

  @Override
  public Optional<Project> get(ProjectPath path) {
    Assert.notNull(PATH_PARAMETER, path);

    return downloader.download(path);
  }

  @Override
  public void save(ProjectHistory history) {
    Assert.notNull("history", history);

    try {
      Path historyPath = historyFilePath(history.path());

      Files.createDirectories(historyPath.getParent());
      Files.write(historyPath, writer.writeValueAsBytes(PersistedProjectHistory.from(history)));
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error saving history: " + e.getMessage(), e);
    }
  }

  @Override
  public ProjectHistory getHistory(ProjectPath path) {
    Assert.notNull(PATH_PARAMETER, path);

    Path historyFilePath = historyFilePath(path);

    if (Files.notExists(historyFilePath)) {
      return ProjectHistory.empty(path);
    }

    try {
      return json.readValue(Files.readAllBytes(historyFilePath), PersistedProjectHistory.class).toDomain(path);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Can't read project history: " + e.getMessage(), e);
    }
  }

  private Path historyFilePath(ProjectPath path) {
    return Path.of(path.get(), HISTORY_FOLDER, HISTORY_FILE);
  }
}
