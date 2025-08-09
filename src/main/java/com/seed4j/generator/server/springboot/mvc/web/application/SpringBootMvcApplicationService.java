package com.seed4j.generator.server.springboot.mvc.web.application;

import com.seed4j.generator.server.springboot.mvc.web.domain.SpringBootMvcModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootMvcApplicationService {

  private final SpringBootMvcModuleFactory springBootMvcs;

  public SpringBootMvcApplicationService() {
    springBootMvcs = new SpringBootMvcModuleFactory();
  }

  public SeedModule buildTomcatModule(SeedModuleProperties properties) {
    return springBootMvcs.buildTomcatModule(properties);
  }

  public SeedModule buildUndertowModule(SeedModuleProperties properties) {
    return springBootMvcs.buildUndertowModule(properties);
  }

  public SeedModule buildEmptyModule(SeedModuleProperties properties) {
    return springBootMvcs.buildEmptyModule(properties);
  }
}
