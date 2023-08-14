package tech.jhipster.lite.module.infrastructure.secondary;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.module.domain.javaproperties.SpringComment;
import tech.jhipster.lite.module.domain.javaproperties.SpringComments;
import tech.jhipster.lite.module.domain.javaproperties.SpringPropertiesBlockComment;
import tech.jhipster.lite.module.domain.javaproperties.SpringPropertiesBlockComments;
import tech.jhipster.lite.module.domain.javaproperties.SpringPropertyType;
import tech.jhipster.lite.module.domain.javaproperties.SpringPropertyTypeFileName;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.error.domain.Assert;

@Service
class FileSystemSpringCommentsCommandsHandler {

  private static final Map<SpringPropertyType, List<String>> PROPERTIES_PATHS = FileSystemJHipsterModulesRepository.buildPaths();

  public void handle(JHipsterProjectFolder projectFolder, SpringComments comments, SpringPropertiesBlockComments propertiesBlockComments) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("comments", comments);
    Assert.notNull("propertiesBlockComments", propertiesBlockComments);

    comments.get().forEach(setComment(projectFolder));
    propertiesBlockComments.get().forEach(propertiesBlockComment(projectFolder));
  }

  private Consumer<SpringComment> setComment(JHipsterProjectFolder projectFolder) {
    return comment -> {
      Optional<Path> path = getPath(projectFolder, comment);
      if (path.isPresent()) {
        new PropertiesFileSpringCommentsHandler(path.get()).set(comment.key(), comment.value());
      }
    };
  }

  private Consumer<SpringPropertiesBlockComment> propertiesBlockComment(JHipsterProjectFolder projectFolder) {
    return propertiesBlockComment -> {
      Optional<Path> path = getPath(projectFolder, propertiesBlockComment);
      if (path.isPresent()) {
        new PropertiesFileSpringPropertiesBlockCommentsHandler(path.get())
          .set(propertiesBlockComment.comment(), propertiesBlockComment.properties());
      }
    };
  }

  private static Optional<Path> getPath(JHipsterProjectFolder projectFolder, SpringPropertyTypeFileName springPropertyTypeFileName) {
    return PROPERTIES_PATHS
      .get(springPropertyTypeFileName.type())
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
