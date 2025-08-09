package com.seed4j.generator.server.springboot.apidocumentation.springdocokta.application;

import com.seed4j.generator.server.springboot.apidocumentation.springdocokta.domain.SpringdocOktaModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringdocOktaApplicationService {

  private final SpringdocOktaModuleFactory springdocOkta;

  public SpringdocOktaApplicationService() {
    springdocOkta = new SpringdocOktaModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return springdocOkta.buildModule(properties);
  }
}
