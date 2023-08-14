package tech.jhipster.lite.shared.error_generator.infrastructure.primary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.module.domain.docker.DockerImageName;
import tech.jhipster.lite.module.domain.docker.UnknownDockerImageException;

@RestController
@RequestMapping("/api/generator-errors")
class GeneratorErrorsResource {

  @GetMapping("unknown-docker-image")
  void getUnknownDockerImage() {
    throw new UnknownDockerImageException(new DockerImageName("unknown-image"));
  }
}
