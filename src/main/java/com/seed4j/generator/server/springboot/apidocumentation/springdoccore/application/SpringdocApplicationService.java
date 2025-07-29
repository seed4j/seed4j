package com.seed4j.generator.server.springboot.apidocumentation.springdoccore.application;

import com.seed4j.generator.server.springboot.apidocumentation.springdoccore.domain.SpringdocModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringdocApplicationService {

  private final SpringdocModuleFactory springdoc;

  public SpringdocApplicationService() {
    springdoc = new SpringdocModuleFactory();
  }

  public JHipsterModule buildSpringdocMvcModule(JHipsterModuleProperties properties) {
    return springdoc.buildModuleForMvc(properties);
  }

  public JHipsterModule buildSpringdocWebfluxModule(JHipsterModuleProperties properties) {
    return springdoc.buildModuleForWebflux(properties);
  }
}
