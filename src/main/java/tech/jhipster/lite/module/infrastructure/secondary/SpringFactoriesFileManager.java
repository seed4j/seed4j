package tech.jhipster.lite.module.infrastructure.secondary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.error.domain.GeneratorException;

public class SpringFactoriesFileManager {

  private final Path file;

  public SpringFactoriesFileManager(Path file) {
    Assert.notNull("file", file);
    this.file = file;
  }

  public String readOrInit() throws IOException {
    if (Files.notExists(file)) {
      Files.createDirectories(file.getParent());
      Files.createFile(file);
      return "";
    }
    return Files.readString(file);
  }

  public void write(String content) throws IOException {
    try {
      Files.writeString(file, content);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error writing to Spring Factories file: " + e.getMessage(), e);
    }
  }
}
