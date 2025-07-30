package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.module.domain.standalonedocker.JHipsterModuleDockerComposeFile;
import com.seed4j.module.domain.startupcommand.DockerComposeFile;
import com.seed4j.shared.error.domain.Assert;
import java.nio.file.Path;
import java.util.function.Consumer;

class FileSystemDockerComposeFileHandler {

  public static final String COMPOSE_FILE_NAME = "docker-compose.yml";

  public void handle(JHipsterProjectFolder projectFolder, JHipsterModuleDockerComposeFile files) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("files", files);

    files.dockerComposeFiles().get().forEach(include(projectFolder));
  }

  private Consumer<DockerComposeFile> include(JHipsterProjectFolder projectFolder) {
    return file -> new DockerComposeFileHandler(getPath(projectFolder)).append(file);
  }

  private static Path getPath(JHipsterProjectFolder projectFolder) {
    return projectFolder.filePath(COMPOSE_FILE_NAME);
  }
}
