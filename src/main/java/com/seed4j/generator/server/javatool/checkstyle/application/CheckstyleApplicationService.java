package com.seed4j.generator.server.javatool.checkstyle.application;

import com.seed4j.generator.server.javatool.checkstyle.domain.CheckstyleModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Component;

@Component
public class CheckstyleApplicationService {

  private final CheckstyleModuleFactory checkstyle;

  public CheckstyleApplicationService() {
    checkstyle = new CheckstyleModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return checkstyle.buildModule(properties);
  }
}
