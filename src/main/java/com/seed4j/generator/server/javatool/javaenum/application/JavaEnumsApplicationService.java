package com.seed4j.generator.server.javatool.javaenum.application;

import com.seed4j.generator.server.javatool.javaenum.domain.JavaEnumsModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JavaEnumsApplicationService {

  private final JavaEnumsModuleFactory javaEnums;

  public JavaEnumsApplicationService() {
    javaEnums = new JavaEnumsModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return javaEnums.buildModule(properties);
  }
}
