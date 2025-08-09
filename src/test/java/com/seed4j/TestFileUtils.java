package com.seed4j;

import static java.nio.file.StandardCopyOption.*;

import com.seed4j.module.domain.properties.SeedProjectFolder;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.stream.Stream;

public final class TestFileUtils {

  private TestFileUtils() {}

  public static String tmpDirForTest() {
    return Path.of(tmpDir()).resolve("seed4j-test").resolve(UUID.randomUUID().toString()).toString().replace("\\", "/");
  }

  private static String tmpDir() {
    String tempDir = System.getProperty("java.io.tmpdir");
    String fileSeparator = FileSystems.getDefault().getSeparator();

    if (tempDir.endsWith(fileSeparator)) {
      return tempDir.substring(0, tempDir.length() - fileSeparator.length());
    }

    return tempDir;
  }

  public static String content(Path path) {
    try {
      return Files.readString(path);
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  public static String contentNormalizingNewLines(Path path) {
    return content(path).replace("\r\n", "\n");
  }

  public static void loadDefaultProperties(Path from, Path to) {
    try {
      Files.createDirectories(to.getParent());

      Files.copy(from, to);
    } catch (IOException e) {
      throw new AssertionError(e.getMessage(), e);
    }
  }

  public static SeedProjectFolder projectFrom(String sourceProject) {
    Path targetFolder = Path.of(tmpDirForTest());

    try {
      Files.createDirectories(targetFolder);
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    try {
      copyFolder(Path.of(sourceProject), targetFolder);
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    return new SeedProjectFolder(targetFolder.toString());
  }

  public static void copyFolder(Path src, Path dest) throws IOException {
    try (Stream<Path> stream = Files.walk(src)) {
      stream.forEach(source -> copy(source, dest.resolve(src.relativize(source))));
    }
  }

  public static void copy(Path source, Path dest) {
    try {
      Files.copy(source, dest, REPLACE_EXISTING);
    } catch (Exception e) {
      throw new AssertionError(e.getMessage(), e) {};
    }
  }
}
