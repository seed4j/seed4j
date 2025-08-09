package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.GeneratedProjectRepository;
import com.seed4j.module.domain.JHipsterFileMatcher;
import com.seed4j.module.domain.JHipsterProjectFilesPaths;
import com.seed4j.module.domain.SeedProjectFilePath;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.error.domain.GeneratorException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
class FileSystemGeneratedProjectRepository implements GeneratedProjectRepository {

  @Override
  public JHipsterProjectFilesPaths list(SeedProjectFolder folder, JHipsterFileMatcher files) {
    Assert.notNull("folder", folder);
    Assert.notNull("files", files);

    try (Stream<Path> content = Files.walk(Path.of(folder.get()))) {
      return new JHipsterProjectFilesPaths(
        content
          .filter(file -> !Files.isDirectory(file))
          .map(Path::toString)
          .map(file -> file.substring(folder.get().length() + 1))
          .map(SeedProjectFilePath::new)
          .filter(files::match)
          .toList()
      );
    } catch (IOException e) {
      throw GeneratorException.technicalError(e.getMessage(), e);
    }
  }
}
