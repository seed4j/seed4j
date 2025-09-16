package com.seed4j.generator.server.javatool.checkstyle.application;

import com.seed4j.generator.server.javatool.checkstyle.domain.CheckstyleModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Component;

@Component
public class CheckstyleApplicationService {

  private final CheckstyleModuleFactory checkstyle;

  public CheckstyleApplicationService() {
    checkstyle = new CheckstyleModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return checkstyle.buildModule(properties);
  }
}
