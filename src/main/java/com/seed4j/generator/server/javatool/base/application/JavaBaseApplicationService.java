package com.seed4j.generator.server.javatool.base.application;

import com.seed4j.generator.server.javatool.base.domain.JavaBaseModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JavaBaseApplicationService {

  private final JavaBaseModuleFactory javaBase;

  public JavaBaseApplicationService() {
    javaBase = new JavaBaseModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return javaBase.buildModule(properties);
  }
}
