package com.seed4j.generator.server.springboot.docker.application;

import com.seed4j.generator.server.springboot.docker.domain.SpringBootDockerModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootDockerApplicationService {

  private final SpringBootDockerModuleFactory springBootDocker;

  public SpringBootDockerApplicationService() {
    springBootDocker = new SpringBootDockerModuleFactory();
  }

  public Seed4JModule buildJibModule(Seed4JModuleProperties properties) {
    return springBootDocker.buildJibModule(properties);
  }

  public Seed4JModule buildDockerFileMavenModule(Seed4JModuleProperties properties) {
    return springBootDocker.buildDockerFileMavenModule(properties);
  }

  public Seed4JModule buildDockerFileGradleModule(Seed4JModuleProperties properties) {
    return springBootDocker.buildDockerFileGradleModule(properties);
  }

  public Seed4JModule buildSpringBootDockerComposeModule(Seed4JModuleProperties properties) {
    return springBootDocker.buildSpringBootDockerComposeModule(properties);
  }
}
