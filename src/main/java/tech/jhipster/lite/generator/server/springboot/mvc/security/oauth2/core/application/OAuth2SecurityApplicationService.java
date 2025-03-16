package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.core.domain.OAuth2ModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class OAuth2SecurityApplicationService {

  private final OAuth2ModuleFactory factory;

  public OAuth2SecurityApplicationService(DockerImages dockerImages) {
    factory = new OAuth2ModuleFactory(dockerImages);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
