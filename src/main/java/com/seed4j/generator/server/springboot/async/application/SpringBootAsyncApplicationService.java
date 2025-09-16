package com.seed4j.generator.server.springboot.async.application;

import com.seed4j.generator.server.springboot.async.domain.SpringBootAsyncModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootAsyncApplicationService {

  private final SpringBootAsyncModuleFactory springBootAsync;

  public SpringBootAsyncApplicationService() {
    springBootAsync = new SpringBootAsyncModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return springBootAsync.buildModule(properties);
  }
}
