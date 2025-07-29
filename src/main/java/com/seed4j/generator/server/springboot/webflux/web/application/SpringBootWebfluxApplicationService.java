package com.seed4j.generator.server.springboot.webflux.web.application;

import com.seed4j.generator.server.springboot.webflux.web.domain.SpringBootWebfluxModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootWebfluxApplicationService {

  private final SpringBootWebfluxModuleFactory springBootWebflux;

  public SpringBootWebfluxApplicationService() {
    springBootWebflux = new SpringBootWebfluxModuleFactory();
  }

  public JHipsterModule buildNettyModule(JHipsterModuleProperties properties) {
    return springBootWebflux.buildNettyModule(properties);
  }

  public JHipsterModule buildEmptyModule(JHipsterModuleProperties properties) {
    return springBootWebflux.buildEmptyModule(properties);
  }
}
