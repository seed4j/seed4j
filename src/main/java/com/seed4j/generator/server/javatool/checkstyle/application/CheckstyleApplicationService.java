package com.seed4j.generator.server.javatool.checkstyle.application;

import com.seed4j.generator.server.javatool.checkstyle.domain.CheckstyleModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Component;

@Component
public class CheckstyleApplicationService {

  private final CheckstyleModuleFactory checkstyle;

  public CheckstyleApplicationService() {
    checkstyle = new CheckstyleModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return checkstyle.buildModule(properties);
  }
}
