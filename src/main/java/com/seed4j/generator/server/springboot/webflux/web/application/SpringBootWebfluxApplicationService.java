package com.seed4j.generator.server.springboot.webflux.web.application;

import com.seed4j.generator.server.springboot.webflux.web.domain.SpringBootWebfluxModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootWebfluxApplicationService {

  private final SpringBootWebfluxModuleFactory springBootWebflux;

  public SpringBootWebfluxApplicationService() {
    springBootWebflux = new SpringBootWebfluxModuleFactory();
  }

  public Seed4JModule buildNettyModule(Seed4JModuleProperties properties) {
    return springBootWebflux.buildNettyModule(properties);
  }

  public Seed4JModule buildEmptyModule(Seed4JModuleProperties properties) {
    return springBootWebflux.buildEmptyModule(properties);
  }
}
