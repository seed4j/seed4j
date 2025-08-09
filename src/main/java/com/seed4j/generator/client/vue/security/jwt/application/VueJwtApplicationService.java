package com.seed4j.generator.client.vue.security.jwt.application;

import com.seed4j.generator.client.vue.security.jwt.domain.VueJwtModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class VueJwtApplicationService {

  private final VueJwtModuleFactory vueJwt;

  public VueJwtApplicationService() {
    vueJwt = new VueJwtModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return vueJwt.buildModule(properties);
  }
}
