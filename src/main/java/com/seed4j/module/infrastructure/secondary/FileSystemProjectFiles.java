package com.seed4j.module.infrastructure.secondary;

import com.google.errorprone.annotations.MustBeClosed;
import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.error.domain.GeneratorException;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

@Service
public class FileSystemProjectFiles implements ProjectFiles {

  private static final String SLASH = "/";

  @Override
  @ExcludeFromGeneratedCodeCoverage(reason = "The error handling is an hard to test implementation detail")
  public String readString(String path) {
    Assert.notBlank("path", path);

    try (InputStream input = getInputStream(path)) {
      assertFileExist(path, input);

      return toString(input);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error closing " + path + ": " + e.getMessage(), e);
    }
  }

  private InputStream getInputStream(String path) {
    return FileSystemProjectFiles.class.getResourceAsStream(path.replace("\\", SLASH));
  }

  private void assertFileExist(String path, InputStream input) {
    if (input == null) {
      throw GeneratorException.technicalError("Can't find file: " + path);
    }
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "The error handling is an hard to test implementation detail")
  private static String toString(InputStream input) {
    try {
      return IOUtils.toString(input, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error reading file: " + e.getMessage(), e);
    }
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage(reason = "The error handling is an hard to test implementation detail")
  public byte[] readBytes(String path) {
    Assert.notBlank("path", path);

    try (InputStream input = getInputStream(path)) {
      assertFileExist(path, input);

      return toByteArray(input);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error closing " + path + ": " + e.getMessage(), e);
    }
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "The error handling is an hard to test implementation detail")
  private static byte[] toByteArray(InputStream input) {
    try {
      return IOUtils.toByteArray(input);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error reading file: " + e.getMessage(), e);
    }
  }

  @Override
  public Collection<String> findRecursivelyInPath(String rootFolder) {
    Assert.notBlank("rootFolder", rootFolder);

    Path rootPath = rootPathFrom(rootFolder);

    assertIsDirectory(rootPath);

    return buildRelativePath(rootFolder, rootPath);
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "The error handling is an hard to test implementation detail")
  private Path rootPathFrom(String resourcePath) {
    URL folderUrl = getURL(resourcePath);
    assertFolderExist(resourcePath, folderUrl);

    try {
      return Path.of(folderUrl.toURI());
    } catch (URISyntaxException e) {
      throw GeneratorException.technicalError("Unable to read folder %s: %s".formatted(resourcePath, e.getMessage()), e);
    }
  }

  private void assertFolderExist(String path, URL url) {
    if (url == null) {
      throw GeneratorException.technicalError("Can't find folder: " + path);
    }
  }

  private void assertIsDirectory(Path rootPath) {
    if (!Files.isDirectory(rootPath)) {
      throw GeneratorException.technicalError("Path %s is not a folder".formatted(rootPath));
    }
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "The error handling is an hard to test implementation detail")
  private static List<String> buildRelativePath(String rootFolder, Path rootPath) {
    try (Stream<Path> walkStream = getWalkStream(rootPath)) {
      return walkStream.filter(Files::isRegularFile).map(relativePathFrom(rootFolder, rootPath)).toList();
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error closing %s: %s".formatted(rootFolder, e.getMessage()), e);
    }
  }

  @MustBeClosed
  private static Stream<Path> getWalkStream(Path folder) throws IOException {
    return Files.walk(folder);
  }

  private static Function<Path, String> relativePathFrom(String rootFolder, Path rootPath) {
    return path -> {
      Path relativePath = rootPath.relativize(path);
      String relativePathString = relativePath.toString().replace("\\", SLASH);
      return rootFolder + SLASH + relativePathString;
    };
  }

  private URL getURL(String path) {
    return FileSystemProjectFiles.class.getResource(path.replace("\\", SLASH));
  }
}
