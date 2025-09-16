package com.seed4j.generator.server.springboot.apidocumentation.springdocauth0.application;

import com.seed4j.generator.server.springboot.apidocumentation.springdocauth0.domain.SpringdocAuth0ModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringdocAuth0ApplicationService {

  private final SpringdocAuth0ModuleFactory springdocAuth0;

  public SpringdocAuth0ApplicationService() {
    springdocAuth0 = new SpringdocAuth0ModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return springdocAuth0.buildModule(properties);
  }
}
