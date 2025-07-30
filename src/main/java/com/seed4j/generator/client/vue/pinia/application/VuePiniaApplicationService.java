package com.seed4j.generator.client.vue.pinia.application;

import com.seed4j.generator.client.vue.pinia.domain.VuePiniaModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class VuePiniaApplicationService {

  private final VuePiniaModuleFactory vue;

  public VuePiniaApplicationService() {
    vue = new VuePiniaModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return vue.buildModule(properties);
  }
}
