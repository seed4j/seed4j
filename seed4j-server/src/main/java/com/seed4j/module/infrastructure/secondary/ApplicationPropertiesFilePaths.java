package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.javaproperties.ApplicationPropertiesFile;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

final class ApplicationPropertiesFilePaths {

  private ApplicationPropertiesFilePaths() {}

  static Optional<Path> existing(Seed4JProjectFolder projectFolder, ApplicationPropertiesFile file, String extension) {
    return file
      .folders()
      .stream()
      .map(folder -> path(projectFolder, folder, file, extension))
      .filter(Files::exists)
      .findFirst();
  }

  static Path existingOrDefault(Seed4JProjectFolder projectFolder, ApplicationPropertiesFile file, String extension) {
    return existing(projectFolder, file, extension).orElseGet(() -> path(projectFolder, file.folders().getFirst(), file, extension));
  }

  private static Path path(Seed4JProjectFolder projectFolder, String folder, ApplicationPropertiesFile file, String extension) {
    return projectFolder.filePath(folder).resolve(file.filename() + extension);
  }
}
