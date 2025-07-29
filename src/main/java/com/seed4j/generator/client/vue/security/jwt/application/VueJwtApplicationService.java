package com.seed4j.generator.client.vue.security.jwt.application;

import com.seed4j.generator.client.vue.security.jwt.domain.VueJwtModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class VueJwtApplicationService {

  private final VueJwtModuleFactory vueJwt;

  public VueJwtApplicationService() {
    vueJwt = new VueJwtModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return vueJwt.buildModule(properties);
  }
}
