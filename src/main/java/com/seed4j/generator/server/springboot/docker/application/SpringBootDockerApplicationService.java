package com.seed4j.generator.server.springboot.docker.application;

import com.seed4j.generator.server.springboot.docker.domain.SpringBootDockerModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

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

  public JHipsterModule buildSpringBootDockerComposeModule(JHipsterModuleProperties properties) {
    return springBootDocker.buildSpringBootDockerComposeModule(properties);
  }
}
