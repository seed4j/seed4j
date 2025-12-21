package com.seed4j.generator.server.springboot.mvc.web.application;

import com.seed4j.generator.server.springboot.mvc.web.domain.SpringBootMvcModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootMvcApplicationService {

  private final SpringBootMvcModuleFactory springBootMvcs;

  public SpringBootMvcApplicationService() {
    springBootMvcs = new SpringBootMvcModuleFactory();
  }

  public Seed4JModule buildTomcatModule(Seed4JModuleProperties properties) {
    return springBootMvcs.buildTomcatModule(properties);
  }

  public Seed4JModule buildEmptyModule(Seed4JModuleProperties properties) {
    return springBootMvcs.buildEmptyModule(properties);
  }
}
