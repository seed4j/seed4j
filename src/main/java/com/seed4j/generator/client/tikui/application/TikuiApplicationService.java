package com.seed4j.generator.client.tikui.application;

import com.seed4j.generator.client.tikui.domain.TikuiModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class TikuiApplicationService {

  private final TikuiModuleFactory tikui = new TikuiModuleFactory();

  public SeedModule buildModule(SeedModuleProperties properties) {
    return tikui.buildModule(properties);
  }
}
