package com.seed4j.generator.server.springboot.thymeleaf.init.application;

import com.seed4j.generator.server.springboot.thymeleaf.init.domain.SpringBootThymeleafModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootThymeleafApplicationService {

  private final SpringBootThymeleafModuleFactory springBootThymeleaf;

  public SpringBootThymeleafApplicationService() {
    springBootThymeleaf = new SpringBootThymeleafModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return springBootThymeleaf.buildModule(properties);
  }
}
