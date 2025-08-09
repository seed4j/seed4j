package com.seed4j.generator.client.vue.pinia.application;

import com.seed4j.generator.client.vue.pinia.domain.VuePiniaModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class VuePiniaApplicationService {

  private final VuePiniaModuleFactory vue;

  public VuePiniaApplicationService() {
    vue = new VuePiniaModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return vue.buildModule(properties);
  }
}
