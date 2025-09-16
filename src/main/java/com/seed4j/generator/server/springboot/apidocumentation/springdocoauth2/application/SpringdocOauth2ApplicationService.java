package com.seed4j.generator.server.springboot.apidocumentation.springdocoauth2.application;

import com.seed4j.generator.server.springboot.apidocumentation.springdocoauth2.domain.SpringdocOauth2ModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringdocOauth2ApplicationService {

  private final SpringdocOauth2ModuleFactory springdocOauth2;

  public SpringdocOauth2ApplicationService() {
    springdocOauth2 = new SpringdocOauth2ModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return springdocOauth2.buildModule(properties);
  }
}
