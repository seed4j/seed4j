package com.seed4j.generator.client.tikui.application;

import com.seed4j.generator.client.tikui.domain.TikuiModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class TikuiApplicationService {

  private final TikuiModuleFactory tikui = new TikuiModuleFactory();

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return tikui.buildModule(properties);
  }
}
