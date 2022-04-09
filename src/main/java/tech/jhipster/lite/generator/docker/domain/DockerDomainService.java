package tech.jhipster.lite.generator.docker.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.DEPENDENCIES_FOLDER;
import static tech.jhipster.lite.generator.tools.domain.Constants.DOCKERFILE;
import static tech.jhipster.lite.generator.tools.domain.Constants.DOCKERFILE_IMAGE_FROM_PREFIX;
import static tech.jhipster.lite.generator.tools.domain.Constants.DOCKER_IMAGE_NAME_VERSION_SEPARATOR;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEMPLATE_FOLDER;

import java.util.Optional;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.Assert;

public class DockerDomainService implements DockerService {

  @Override
  public Optional<String> getImageNameWithVersion(String imageName) {
    return getImageVersion(imageName).map(version -> imageName + DOCKER_IMAGE_NAME_VERSION_SEPARATOR + version);
  }

  @Override
  public Optional<String> getImageVersion(String imageName) {
    Assert.notBlank("imageName", imageName);

    String dockerfilePath = getPath(TEMPLATE_FOLDER, DEPENDENCIES_FOLDER, DOCKERFILE);
    String fromImageNameValue = DOCKERFILE_IMAGE_FROM_PREFIX + " " + imageName + DOCKER_IMAGE_NAME_VERSION_SEPARATOR;

    return FileUtils
      .readLinesInClasspath(dockerfilePath)
      .stream()
      .map(String::trim)
      .filter(line -> line.toLowerCase().startsWith(fromImageNameValue.toLowerCase()))
      .map(line -> line.split(DOCKER_IMAGE_NAME_VERSION_SEPARATOR))
      .filter(splits -> splits.length == 2)
      .map(splits -> splits[1].trim())
      .findFirst();
  }
}
