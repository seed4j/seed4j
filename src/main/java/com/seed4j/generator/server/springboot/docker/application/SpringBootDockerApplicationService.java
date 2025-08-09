package com.seed4j.generator.server.springboot.docker.application;

import com.seed4j.generator.server.springboot.docker.domain.SpringBootDockerModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootDockerApplicationService {

  private final SpringBootDockerModuleFactory springBootDocker;

  public SpringBootDockerApplicationService() {
    springBootDocker = new SpringBootDockerModuleFactory();
  }

  public JHipsterModule buildJibModule(SeedModuleProperties properties) {
    return springBootDocker.buildJibModule(properties);
  }

  public JHipsterModule buildDockerFileMavenModule(SeedModuleProperties properties) {
    return springBootDocker.buildDockerFileMavenModule(properties);
  }

  public JHipsterModule buildDockerFileGradleModule(SeedModuleProperties properties) {
    return springBootDocker.buildDockerFileGradleModule(properties);
  }

  public JHipsterModule buildSpringBootDockerComposeModule(SeedModuleProperties properties) {
    return springBootDocker.buildSpringBootDockerComposeModule(properties);
  }
}
