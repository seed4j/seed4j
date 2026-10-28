package com.seed4j.project.infrastructure.secondary;

import com.seed4j.project.domain.ProjectPath;
import com.seed4j.project.domain.ProjectsRepository;
import com.seed4j.project.domain.download.Project;
import com.seed4j.project.domain.history.ProjectAction;
import com.seed4j.project.domain.history.ProjectHistory;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.error.domain.GeneratorException;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.stereotype.Repository;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

@Repository
class FileSystemProjectsRepository implements ProjectsRepository {

  private static final String PATH_PARAMETER = "path";

  private static final String HISTORY_FOLDER = ".seed4j/modules";
  private static final String HISTORY_FILE = "history.json";

  private static final DateTimeFormatter FILENAME_DATE_FORMAT_WITHOUT_MS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(
    ZoneId.of("UTC")
  );
  private static final DateTimeFormatter FILENAME_DATE_FORMAT_WITH_MS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").withZone(
    ZoneId.of("UTC")
  );
  private static final Comparator<Path> ACTION_FILES_COMPARATOR = Comparator.comparing(path ->
    parseFilename(path.getFileName().toString().toLowerCase(Locale.ROOT))
  );

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
  public void save(ProjectPath path, ProjectAction action) {
    Assert.notNull("action", action);

    try {
      Path historyPath = Path.of(path.get(), HISTORY_FOLDER, moduleActionFilename(action));

      Files.createDirectories(historyPath.getParent());
      Files.write(historyPath, writer.writeValueAsBytes(PersistedProjectAction.from(action)));
    } catch (JacksonException | IOException e) {
      throw GeneratorException.technicalError("Error saving action: " + e.getMessage(), e);
    }
  }

  private static String moduleActionFilename(final ProjectAction action) {
    return FILENAME_DATE_FORMAT_WITH_MS.format(action.date()) + "-" + action.module().get() + ".json";
  }

  @Override
  public ProjectHistory getHistory(ProjectPath path) {
    Assert.notNull(PATH_PARAMETER, path);

    Path historyFilePath = Path.of(path.get(), HISTORY_FOLDER, HISTORY_FILE);

    PersistedProjectHistory projectHistory = initialHistory(historyFilePath);
    projectHistory.addAll(specificModulesHistory(path));

    return projectHistory.toDomain(path);
  }

  private PersistedProjectHistory initialHistory(final Path historyFilePath) {
    if (Files.notExists(historyFilePath)) {
      return PersistedProjectHistory.EMPTY;
    }

    return read(PersistedProjectHistory.class, historyFilePath);
  }

  @ExcludeFromGeneratedCodeCoverage
  private Collection<PersistedProjectAction> specificModulesHistory(ProjectPath projectPath) {
    Path historyFolder = Path.of(projectPath.get(), HISTORY_FOLDER);

    if (Files.notExists(historyFolder)) {
      return List.of();
    }

    try (var stream = Files.list(historyFolder)) {
      return stream
        .filter(actionHistory())
        .sorted(ACTION_FILES_COMPARATOR)
        .map(actionFile -> read(PersistedProjectAction.class, actionFile))
        .toList();
    } catch (JacksonException | IOException e) {
      throw GeneratorException.technicalError("Can't read project history files: " + e.getMessage(), e);
    }
  }

  private static Predicate<Path> actionHistory() {
    return path -> {
      String filename = path.toString().toLowerCase(Locale.ROOT);

      return filename.endsWith(".json") && !filename.endsWith(HISTORY_FILE);
    };
  }

  private <T> T read(Class<T> clazz, Path historyFilePath) {
    try {
      return json.readValue(Files.readAllBytes(historyFilePath), clazz);
    } catch (JacksonException | IOException e) {
      throw GeneratorException.technicalError("Can't read project history: " + e.getMessage(), e);
    }
  }

  private static LocalDateTime parseFilename(String filename) {
    String timestamp = filename.substring(0, filename.indexOf("-"));
    try {
      return LocalDateTime.parse(timestamp, FILENAME_DATE_FORMAT_WITH_MS);
    } catch (DateTimeParseException _) {
      return LocalDateTime.parse(timestamp, FILENAME_DATE_FORMAT_WITHOUT_MS);
    }
  }
}
