package com.seed4j.generator.client.vue.router.application;

import com.seed4j.generator.client.vue.router.domain.VueRouterModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class VueRouterApplicationService {

  private final VueRouterModuleFactory vue;

  public VueRouterApplicationService() {
    vue = new VueRouterModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return vue.buildModule(properties);
  }
}
