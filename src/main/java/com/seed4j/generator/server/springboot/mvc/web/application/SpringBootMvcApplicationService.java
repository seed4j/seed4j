package com.seed4j.generator.server.springboot.mvc.web.application;

import com.seed4j.generator.server.springboot.mvc.web.domain.SpringBootMvcModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootMvcApplicationService {

  private final SpringBootMvcModuleFactory springBootMvcs;

  public SpringBootMvcApplicationService() {
    springBootMvcs = new SpringBootMvcModuleFactory();
  }

  public JHipsterModule buildTomcatModule(SeedModuleProperties properties) {
    return springBootMvcs.buildTomcatModule(properties);
  }

  public JHipsterModule buildUndertowModule(SeedModuleProperties properties) {
    return springBootMvcs.buildUndertowModule(properties);
  }

  public JHipsterModule buildEmptyModule(SeedModuleProperties properties) {
    return springBootMvcs.buildEmptyModule(properties);
  }
}
