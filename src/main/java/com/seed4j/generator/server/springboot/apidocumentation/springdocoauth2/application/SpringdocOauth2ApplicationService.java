package com.seed4j.generator.server.springboot.apidocumentation.springdocoauth2.application;

import com.seed4j.generator.server.springboot.apidocumentation.springdocoauth2.domain.SpringdocOauth2ModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringdocOauth2ApplicationService {

  private final SpringdocOauth2ModuleFactory springdocOauth2;

  public SpringdocOauth2ApplicationService() {
    springdocOauth2 = new SpringdocOauth2ModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return springdocOauth2.buildModule(properties);
  }
}
