package com.seed4j.module.domain.docker;

import com.seed4j.shared.error.domain.GeneratorException;

public class UnknownDockerImageException extends GeneratorException {

  public UnknownDockerImageException(DockerImageName imageName) {
    super(
      internalServerError(DockerErrorKey.UNKNOWN_DOCKER_IMAGE).message(buildMessage(imageName)).addParameter("imageName", imageName.get())
    );
  }

  private static String buildMessage(DockerImageName imageName) {
    return new StringBuilder()
      .append("Can't find image ")
      .append(imageName.get())
      .append(", forgot to add it to src/main/resources/generator/dependencies/Dockerfile?")
      .toString();
  }
}
