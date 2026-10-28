package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.module.domain.standalonedocker.Seed4JModuleDockerComposeFile;
import com.seed4j.module.domain.startupcommand.DockerComposeFile;
import com.seed4j.shared.error.domain.Assert;
import java.nio.file.Path;
import java.util.function.Consumer;

class FileSystemDockerComposeFileHandler {

  public static final String COMPOSE_FILE_NAME = "docker-compose.yml";

  public void handle(Indentation indentation, Seed4JProjectFolder projectFolder, Seed4JModuleDockerComposeFile files) {
    Assert.notNull("indentation", indentation);
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("files", files);

    files.dockerComposeFiles().get().forEach(include(indentation, projectFolder));
  }

  private Consumer<DockerComposeFile> include(Indentation indentation, Seed4JProjectFolder projectFolder) {
    return file -> new DockerComposeFileHandler(indentation, getPath(projectFolder)).append(file);
  }

  private static Path getPath(Seed4JProjectFolder projectFolder) {
    return projectFolder.filePath(COMPOSE_FILE_NAME);
  }
}
