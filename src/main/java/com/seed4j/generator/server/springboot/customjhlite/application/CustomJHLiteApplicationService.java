package com.seed4j.generator.server.springboot.customjhlite.application;

import com.seed4j.generator.server.springboot.customjhlite.domain.CustomJHLiteModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CustomJHLiteApplicationService {

  private final CustomJHLiteModuleFactory customJHLite;

  public CustomJHLiteApplicationService() {
    customJHLite = new CustomJHLiteModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return customJHLite.buildModule(properties);
  }
}
