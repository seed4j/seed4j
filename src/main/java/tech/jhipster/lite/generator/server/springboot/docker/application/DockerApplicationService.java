package tech.jhipster.lite.generator.server.springboot.docker.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.docker.domain.DockerModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class DockerApplicationService {

  private final DockerModuleFactory docker;

  public DockerApplicationService() {
    docker = new DockerModuleFactory();
  }

  public JHipsterModule buildJibModule(JHipsterModuleProperties properties) {
    return docker.buildJibModule(properties);
  }

  public JHipsterModule buildDockerFileMavenModule(JHipsterModuleProperties properties) {
    return docker.buildDockerFileMavenModule(properties);
  }

  public JHipsterModule buildDockerFileGradleModule(JHipsterModuleProperties properties) {
    return docker.buildDockerFileGradleModule(properties);
  }
}
