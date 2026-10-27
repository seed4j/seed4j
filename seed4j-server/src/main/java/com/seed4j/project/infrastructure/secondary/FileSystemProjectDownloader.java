package com.seed4j.project.infrastructure.secondary;

import com.seed4j.project.domain.ProjectPath;
import com.seed4j.project.domain.download.Project;
import com.seed4j.project.domain.download.ProjectName;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class FileSystemProjectDownloader {

  private static final Pattern PROJECT_NAME_PATTERN = Pattern.compile("\"description\": *\"([^\"]+)\"");

  public Optional<Project> download(ProjectPath path) {
    Assert.notNull("path", path);

    Path source = Path.of(path.get());

    if (notAProject(source)) {
      return Optional.empty();
    }

    return zip(source)
      .map(content -> {
        ProjectName projectName = readProjectName(source);

        return Optional.of(new Project(projectName, content));
      })
      .orElse(Optional.empty());
  }

  private boolean notAProject(Path source) {
    return Files.notExists(source) || !Files.isDirectory(source);
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Hard to test exception handling")
  private ProjectName readProjectName(Path source) {
    Path packageJsonPath = source.resolve("package.json");

    if (Files.notExists(packageJsonPath)) {
      return ProjectName.DEFAULT;
    }

    try {
      Matcher matcher = PROJECT_NAME_PATTERN.matcher(Files.readString(packageJsonPath));

      if (!matcher.find()) {
        return ProjectName.DEFAULT;
      }

      return new ProjectName(matcher.group(1));
    } catch (IOException e) {
      throw new ProjectZippingException(e);
    }
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Hard to test exception handling")
  private static Optional<byte[]> zip(Path source) {
    ByteArrayOutputStream result = new ByteArrayOutputStream();

    try (Stream<Path> sourceFiles = Files.walk(source); ZipOutputStream zip = new ZipOutputStream(result)) {
      List<Path> files = sourceFiles.filter(regularFiles()).filter(notNodeModules()).toList();

      if (files.isEmpty()) {
        return Optional.empty();
      }

      files.forEach(appendFileEntry(source, zip));
    } catch (IOException e) {
      throw new ProjectZippingException(e);
    }

    return Optional.of(result.toByteArray());
  }

  private static Predicate<Path> regularFiles() {
    return Files::isRegularFile;
  }

  private static Predicate<Path> notNodeModules() {
    return path -> !path.toString().contains("/node_modules/");
  }

  private static Consumer<Path> appendFileEntry(Path source, ZipOutputStream zip) {
    return path -> {
      ZipEntry zipEntry = new ZipEntry(source.relativize(path).toString());

      appendToZipEntry(zip, path, zipEntry);
    };
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Hard to test exception handling")
  private static void appendToZipEntry(ZipOutputStream zip, Path path, ZipEntry zipEntry) {
    try {
      zip.putNextEntry(zipEntry);
      Files.copy(path, zip);
      zip.closeEntry();
    } catch (IOException e) {
      throw new ProjectZippingException(e);
    }
  }
}
