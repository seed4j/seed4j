package com.seed4j.generator.client.vue.core.application;

import com.seed4j.generator.client.vue.core.domain.VueModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class VueApplicationService {

  private final VueModuleFactory vue;

  public VueApplicationService(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    vue = new VueModuleFactory(nodeLazyPackagesInstaller);
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return vue.buildModule(properties);
  }
}
