package com.seed4j.generator.client.vue.router.application;

import com.seed4j.generator.client.vue.router.domain.VueRouterModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class VueRouterApplicationService {

  private final VueRouterModuleFactory vue;

  public VueRouterApplicationService() {
    vue = new VueRouterModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return vue.buildModule(properties);
  }
}
