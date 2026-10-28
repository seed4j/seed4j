package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.GeneratedProjectRepository;
import com.seed4j.module.domain.Seed4JFileMatcher;
import com.seed4j.module.domain.Seed4JProjectFilePath;
import com.seed4j.module.domain.Seed4JProjectFilesPaths;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
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
  public Seed4JProjectFilesPaths list(Seed4JProjectFolder folder, Seed4JFileMatcher files) {
    Assert.notNull("folder", folder);
    Assert.notNull("files", files);

    try (Stream<Path> content = Files.walk(Path.of(folder.get()))) {
      return new Seed4JProjectFilesPaths(
        content
          .filter(file -> !Files.isDirectory(file))
          .map(Path::toString)
          .map(file -> file.substring(folder.get().length() + 1))
          .map(Seed4JProjectFilePath::new)
          .filter(files::match)
          .toList()
      );
    } catch (IOException e) {
      throw GeneratorException.technicalError(e.getMessage(), e);
    }
  }
}
