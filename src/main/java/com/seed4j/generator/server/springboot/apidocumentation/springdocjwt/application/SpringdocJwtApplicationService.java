package com.seed4j.generator.server.springboot.apidocumentation.springdocjwt.application;

import com.seed4j.generator.server.springboot.apidocumentation.springdocjwt.domain.SpringdocJwtModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringdocJwtApplicationService {

  private final SpringdocJwtModuleFactory springdocJwt;

  public SpringdocJwtApplicationService() {
    springdocJwt = new SpringdocJwtModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return springdocJwt.buildModule(properties);
  }
}
