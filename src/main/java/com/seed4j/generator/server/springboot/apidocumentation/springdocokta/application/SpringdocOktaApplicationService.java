package com.seed4j.generator.server.springboot.apidocumentation.springdocokta.application;

import com.seed4j.generator.server.springboot.apidocumentation.springdocokta.domain.SpringdocOktaModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringdocOktaApplicationService {

  private final SpringdocOktaModuleFactory springdocOkta;

  public SpringdocOktaApplicationService() {
    springdocOkta = new SpringdocOktaModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return springdocOkta.buildModule(properties);
  }
}
