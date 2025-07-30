package com.seed4j.generator.client.tikui.application;

import com.seed4j.generator.client.tikui.domain.TikuiModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class TikuiApplicationService {

  private final TikuiModuleFactory tikui = new TikuiModuleFactory();

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return tikui.buildModule(properties);
  }
}
