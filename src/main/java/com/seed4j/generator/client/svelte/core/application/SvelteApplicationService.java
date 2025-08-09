package com.seed4j.generator.client.svelte.core.application;

import com.seed4j.generator.client.svelte.core.domain.SvelteModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SvelteApplicationService {

  private final SvelteModuleFactory svelte;

  public SvelteApplicationService(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    this.svelte = new SvelteModuleFactory(nodeLazyPackagesInstaller);
  }

  public SeedModule buildModule(SeedModuleProperties project) {
    return svelte.buildModule(project);
  }
}
