package tech.jhipster.lite.generator.docker.application;

import java.util.Optional;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.docker.domain.DockerService;

@Service
public record DockerApplicationService(DockerService dockerService) {
  public Optional<String> getImageNameWithVersion(String imageName) {
    return dockerService.getImageNameWithVersion(imageName);
  }

  public Optional<String> getImageVersion(String imageName) {
    return dockerService.getImageVersion(imageName);
  }
}
