package tech.jhipster.lite.generator.server.sonarqube.application;

import org.springframework.stereotype.Component;
import tech.jhipster.lite.generator.server.sonarqube.domain.SonarQubeModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

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
}
