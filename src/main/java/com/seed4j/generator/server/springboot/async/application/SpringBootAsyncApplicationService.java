package com.seed4j.generator.server.springboot.async.application;

import com.seed4j.generator.server.springboot.async.domain.SpringBootAsyncModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootAsyncApplicationService {

  private final SpringBootAsyncModuleFactory springBootAsync;

  public SpringBootAsyncApplicationService() {
    springBootAsync = new SpringBootAsyncModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return springBootAsync.buildModule(properties);
  }
}
