package com.seed4j.generator.client.vue.security.jwt.application;

import com.seed4j.generator.client.vue.security.jwt.domain.VueJwtModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class VueJwtApplicationService {

  private final VueJwtModuleFactory vueJwt;

  public VueJwtApplicationService() {
    vueJwt = new VueJwtModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return vueJwt.buildModule(properties);
  }
}
