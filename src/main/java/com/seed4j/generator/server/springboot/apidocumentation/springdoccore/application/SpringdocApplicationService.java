package com.seed4j.generator.server.springboot.apidocumentation.springdoccore.application;

import com.seed4j.generator.server.springboot.apidocumentation.springdoccore.domain.SpringdocModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringdocApplicationService {

  private final SpringdocModuleFactory springdoc;

  public SpringdocApplicationService() {
    springdoc = new SpringdocModuleFactory();
  }

  public Seed4JModule buildSpringdocMvcModule(Seed4JModuleProperties properties) {
    return springdoc.buildModuleForMvc(properties);
  }

  public Seed4JModule buildSpringdocWebfluxModule(Seed4JModuleProperties properties) {
    return springdoc.buildModuleForWebflux(properties);
  }
}
