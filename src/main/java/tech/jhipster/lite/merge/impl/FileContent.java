package tech.jhipster.lite.merge.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileContent {

  public static FileContent of(Path path) {
    return new FileContent(path);
  }

  /**
   * sample: /documentation/hexagonal-architecture.md
   */
  private final Path path;

  private Body body;

  /**
   * @param path full path to file. Sample: src/main/resources/config/application.properties
   */
  public FileContent(Path path) {
    this.path = path;
  }

  public FileContent readAllLines() {
    if (path.toFile().exists()) {
      try {
        final List<String> strings = Files.readAllLines(path);
        this.body = new Body(strings);
      } catch (Exception e) {
        this.body = Body.NONE;
      }
    } else {
      body = Body.NONE;
    }
    return this;
  }

  public boolean isFound() {
    return !body.isNone();
  }

  public Path getPath() {
    return path;
  }

  public List<BodyLine> getLines() {
    return body.getLines();
  }
}
