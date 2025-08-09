package com.seed4j.generator.server.springboot.async.application;

import com.seed4j.generator.server.springboot.async.domain.SpringBootAsyncModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootAsyncApplicationService {

  private final SpringBootAsyncModuleFactory springBootAsync;

  public SpringBootAsyncApplicationService() {
    springBootAsync = new SpringBootAsyncModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return springBootAsync.buildModule(properties);
  }
}
