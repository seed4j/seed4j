package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.startupcommand.DockerComposeFile;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.error.domain.GeneratorException;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DockerComposeFileHandler {

  private static final String DASH = "-";
  private static final String LINE_BREAK = System.lineSeparator();
  private static final String SPACER = " ";

  private final Indentation indentation;
  private final Path file;

  public DockerComposeFileHandler(Indentation indentation, Path file) {
    Assert.notNull("indentation", indentation);
    Assert.notNull("file", file);

    this.indentation = indentation;
    this.file = file;
  }

  public void append(DockerComposeFile dockerComposeFile) {
    Assert.notNull("dockerComposeFile", dockerComposeFile);

    updateDockerComposeFile(dockerComposeFile);
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Hard to cover IOException")
  private void updateDockerComposeFile(DockerComposeFile dockerComposeFile) {
    try {
      String properties = buildDockerComposeFile(dockerComposeFile);

      Files.writeString(file, properties);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error updating root compose file: " + e.getMessage(), e);
    }
  }

  private String buildDockerComposeFile(DockerComposeFile dockerComposeFile) throws IOException {
    String currentProperties = readOrInitDockerComposeFile();

    int propertyIndex = currentProperties.indexOf(dockerComposeLine(dockerComposeFile));
    if (propertyIndex != -1) {
      return currentProperties;
    }
    return addNewDockerComposeFile(dockerComposeFile, currentProperties);
  }

  private String addNewDockerComposeFile(DockerComposeFile compose, String currentRootCompose) {
    return currentRootCompose + dockerComposeLine(compose) + LINE_BREAK;
  }

  private String dockerComposeLine(DockerComposeFile compose) {
    return indentation.spaces() + DASH + SPACER + compose.path();
  }

  private String readOrInitDockerComposeFile() throws IOException {
    if (Files.notExists(file)) {
      Files.createDirectories(file.getParent());
      Files.createFile(file);

      return "include:" + LINE_BREAK;
    }

    return Files.readString(file);
  }
}
