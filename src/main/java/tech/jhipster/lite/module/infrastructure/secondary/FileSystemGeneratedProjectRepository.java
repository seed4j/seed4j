package tech.jhipster.lite.module.infrastructure.secondary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.module.domain.GeneratedProjectRepository;
import tech.jhipster.lite.module.domain.JHipsterFileMatcher;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;
import tech.jhipster.lite.module.domain.JHipsterProjectFilesPaths;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.error.domain.GeneratorException;

@Repository
class FileSystemGeneratedProjectRepository implements GeneratedProjectRepository {

  @Override
  public JHipsterProjectFilesPaths list(JHipsterProjectFolder folder, JHipsterFileMatcher files) {
    Assert.notNull("folder", folder);
    Assert.notNull("files", files);

    try (Stream<Path> content = Files.walk(Paths.get(folder.get()))) {
      return new JHipsterProjectFilesPaths(
        content
          .filter(file -> !Files.isDirectory(file))
          .map(Path::toString)
          .map(file -> file.substring(folder.get().length() + 1, file.length()))
          .map(JHipsterProjectFilePath::new)
          .filter(files::match)
          .toList()
      );
    } catch (IOException e) {
      throw GeneratorException.technicalError(e.getMessage(), e);
    }
  }
}
