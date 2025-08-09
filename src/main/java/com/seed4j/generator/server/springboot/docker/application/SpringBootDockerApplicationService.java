package com.seed4j.generator.server.springboot.docker.application;

import com.seed4j.generator.server.springboot.docker.domain.SpringBootDockerModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootDockerApplicationService {

  private final SpringBootDockerModuleFactory springBootDocker;

  public SpringBootDockerApplicationService() {
    springBootDocker = new SpringBootDockerModuleFactory();
  }

  public SeedModule buildJibModule(SeedModuleProperties properties) {
    return springBootDocker.buildJibModule(properties);
  }

  public SeedModule buildDockerFileMavenModule(SeedModuleProperties properties) {
    return springBootDocker.buildDockerFileMavenModule(properties);
  }

  public SeedModule buildDockerFileGradleModule(SeedModuleProperties properties) {
    return springBootDocker.buildDockerFileGradleModule(properties);
  }

  public SeedModule buildSpringBootDockerComposeModule(SeedModuleProperties properties) {
    return springBootDocker.buildSpringBootDockerComposeModule(properties);
  }
}
