package com.seed4j.generator.client.vue.core.application;

import com.seed4j.generator.client.vue.core.domain.VueModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class VueApplicationService {

  private final VueModuleFactory vue;

  public VueApplicationService(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    vue = new VueModuleFactory(nodeLazyPackagesInstaller);
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return vue.buildModule(properties);
  }
}
