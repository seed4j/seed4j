package com.seed4j.generator.server.springboot.apidocumentation.springdocjwt.application;

import com.seed4j.generator.server.springboot.apidocumentation.springdocjwt.domain.SpringdocJwtModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringdocJwtApplicationService {

  private final SpringdocJwtModuleFactory springdocJwt;

  public SpringdocJwtApplicationService() {
    springdocJwt = new SpringdocJwtModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return springdocJwt.buildModule(properties);
  }
}
