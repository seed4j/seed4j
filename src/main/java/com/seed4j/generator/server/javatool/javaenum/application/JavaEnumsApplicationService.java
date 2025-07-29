package com.seed4j.generator.server.javatool.javaenum.application;

import com.seed4j.generator.server.javatool.javaenum.domain.JavaEnumsModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JavaEnumsApplicationService {

  private final JavaEnumsModuleFactory javaEnums;

  public JavaEnumsApplicationService() {
    javaEnums = new JavaEnumsModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return javaEnums.buildModule(properties);
  }
}
