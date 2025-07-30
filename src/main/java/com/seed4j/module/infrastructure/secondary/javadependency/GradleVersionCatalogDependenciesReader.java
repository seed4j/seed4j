package com.seed4j.module.infrastructure.secondary.javadependency;

import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersions;
import com.seed4j.module.infrastructure.secondary.javadependency.gradle.VersionsCatalog;
import com.seed4j.shared.error.domain.GeneratorException;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

@Repository
@Order
class GradleVersionCatalogDependenciesReader implements JavaDependenciesReader {

  private static final String CURRENT_VERSIONS_FILE = "/generator/dependencies/gradle/libs.versions.toml";
  private final VersionsCatalog versionsCatalog;

  @ExcludeFromGeneratedCodeCoverage(reason = "The error handling is an hard to test implementation detail")
  public GradleVersionCatalogDependenciesReader(ProjectFiles files) {
    String tomlConfigContent = files.readString(CURRENT_VERSIONS_FILE);
    try {
      Path tempFile = writeToTemporaryFile(tomlConfigContent);
      versionsCatalog = new VersionsCatalog(tempFile);
    } catch (IOException exception) {
      throw GeneratorException.technicalError(
        "Error creating temporary file for %s content: %s".formatted(CURRENT_VERSIONS_FILE, exception.getMessage()),
        exception
      );
    }
  }

  private static Path writeToTemporaryFile(String tomlConfigContent) throws IOException {
    File tempFile = File.createTempFile("gradle-deps", ".toml", Path.of(System.getProperty("java.io.tmpdir")).toFile());
    Files.writeString(tempFile.toPath(), tomlConfigContent);
    return tempFile.toPath();
  }

  @Override
  public JavaDependenciesVersions get() {
    return new JavaDependenciesVersions(versionsCatalog.retrieveVersions());
  }
}
