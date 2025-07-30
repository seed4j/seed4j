package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.javaproperties.SpringComment;
import com.seed4j.module.domain.javaproperties.SpringComments;
import com.seed4j.module.domain.javaproperties.SpringPropertyType;
import com.seed4j.module.domain.javaproperties.SpringPropertyTypeFileName;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
class FileSystemSpringCommentsCommandsHandler {

  private static final Map<SpringPropertyType, List<String>> PROPERTIES_PATHS = FileSystemJHipsterModulesRepository.buildPaths();

  public void handle(JHipsterProjectFolder projectFolder, SpringComments comments) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("comments", comments);

    comments.get().forEach(setComment(projectFolder));
  }

  private Consumer<SpringComment> setComment(JHipsterProjectFolder projectFolder) {
    return comment ->
      getPath(projectFolder, comment).ifPresent(path -> new PropertiesFileSpringCommentsHandler(path).set(comment.key(), comment.value()));
  }

  private static Optional<Path> getPath(JHipsterProjectFolder projectFolder, SpringPropertyTypeFileName springPropertyTypeFileName) {
    return PROPERTIES_PATHS.get(springPropertyTypeFileName.type())
      .stream()
      .map(toFilePath(projectFolder, springPropertyTypeFileName))
      .filter(Files::exists)
      .findFirst();
  }

  private static Function<String, Path> toFilePath(
    JHipsterProjectFolder projectFolder,
    SpringPropertyTypeFileName springPropertyTypeFileName
  ) {
    return folder -> projectFolder.filePath(folder + propertiesFilename(springPropertyTypeFileName));
  }

  private static String propertiesFilename(SpringPropertyTypeFileName springPropertyTypeFileName) {
    return springPropertyTypeFileName.filename() + ".properties";
  }
}
