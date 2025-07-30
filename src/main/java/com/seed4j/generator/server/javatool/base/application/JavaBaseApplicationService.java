package com.seed4j.generator.server.javatool.base.application;

import com.seed4j.generator.server.javatool.base.domain.JavaBaseModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JavaBaseApplicationService {

  private final JavaBaseModuleFactory javaBase;

  public JavaBaseApplicationService() {
    javaBase = new JavaBaseModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return javaBase.buildModule(properties);
  }
}
