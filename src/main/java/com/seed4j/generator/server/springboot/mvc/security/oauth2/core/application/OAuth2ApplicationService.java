package com.seed4j.generator.server.springboot.mvc.security.oauth2.core.application;

import com.seed4j.generator.server.springboot.mvc.security.oauth2.core.domain.OAuth2ModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class OAuth2ApplicationService {

  private final OAuth2ModuleFactory oAuth2;

  public OAuth2ApplicationService(DockerImages dockerImages) {
    oAuth2 = new OAuth2ModuleFactory(dockerImages);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return oAuth2.buildModule(properties);
  }
}
