package com.seed4j.generator.ci.sonarqube.application;

import com.seed4j.generator.ci.sonarqube.domain.SonarQubeModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Component;

@Component
public class SonarQubeApplicationService {

  private final SonarQubeModuleFactory sonarQube;

  public SonarQubeApplicationService(DockerImages dockerImages) {
    sonarQube = new SonarQubeModuleFactory(dockerImages);
  }

  public JHipsterModule buildBackendModule(JHipsterModuleProperties properties) {
    return sonarQube.buildBackendModule(properties);
  }

  public JHipsterModule buildBackendFrontendModule(JHipsterModuleProperties properties) {
    return sonarQube.buildBackendFrontendModule(properties);
  }

  public JHipsterModule buildTypescriptModule(JHipsterModuleProperties properties) {
    return sonarQube.buildTypescriptModule(properties);
  }
}
