package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.javaproperties.SpringComment;
import com.seed4j.module.domain.javaproperties.SpringComments;
import com.seed4j.module.domain.javaproperties.SpringPropertyType;
import com.seed4j.module.domain.javaproperties.SpringPropertyTypeFileName;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
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

  private static final Map<SpringPropertyType, List<String>> PROPERTIES_PATHS = FileSystemSeed4JModulesRepository.buildPaths();

  public void handle(Seed4JProjectFolder projectFolder, SpringComments comments) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("comments", comments);

    comments.get().forEach(setComment(projectFolder));
  }

  private Consumer<SpringComment> setComment(Seed4JProjectFolder projectFolder) {
    return comment ->
      getPath(projectFolder, comment).ifPresent(path -> new PropertiesFileSpringCommentsHandler(path).set(comment.key(), comment.value()));
  }

  private static Optional<Path> getPath(Seed4JProjectFolder projectFolder, SpringPropertyTypeFileName springPropertyTypeFileName) {
    return PROPERTIES_PATHS.get(springPropertyTypeFileName.type())
      .stream()
      .map(toFilePath(projectFolder, springPropertyTypeFileName))
      .filter(Files::exists)
      .findFirst();
  }

  private static Function<String, Path> toFilePath(
    Seed4JProjectFolder projectFolder,
    SpringPropertyTypeFileName springPropertyTypeFileName
  ) {
    return folder -> projectFolder.filePath(folder + propertiesFilename(springPropertyTypeFileName));
  }

  private static String propertiesFilename(SpringPropertyTypeFileName springPropertyTypeFileName) {
    return springPropertyTypeFileName.filename() + ".properties";
  }
}
