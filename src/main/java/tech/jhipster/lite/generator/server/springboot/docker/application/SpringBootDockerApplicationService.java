package tech.jhipster.lite.generator.server.springboot.docker.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.docker.domain.SpringBootDockerModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringBootDockerApplicationService {

  private final SpringBootDockerModuleFactory springBootDocker;

  public SpringBootDockerApplicationService() {
    springBootDocker = new SpringBootDockerModuleFactory();
  }

  public JHipsterModule buildJibModule(JHipsterModuleProperties properties) {
    return springBootDocker.buildJibModule(properties);
  }

  public JHipsterModule buildDockerFileMavenModule(JHipsterModuleProperties properties) {
    return springBootDocker.buildDockerFileMavenModule(properties);
  }

  public JHipsterModule buildDockerFileGradleModule(JHipsterModuleProperties properties) {
    return springBootDocker.buildDockerFileGradleModule(properties);
  }
}
