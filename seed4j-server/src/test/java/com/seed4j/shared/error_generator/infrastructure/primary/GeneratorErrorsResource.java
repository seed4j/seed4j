package com.seed4j.shared.error_generator.infrastructure.primary;

import com.seed4j.module.domain.docker.DockerImageName;
import com.seed4j.module.domain.docker.UnknownDockerImageException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/generator-errors")
class GeneratorErrorsResource {

  @GetMapping("unknown-docker-image")
  void getUnknownDockerImage() {
    throw new UnknownDockerImageException(new DockerImageName("unknown-image"));
  }
}
