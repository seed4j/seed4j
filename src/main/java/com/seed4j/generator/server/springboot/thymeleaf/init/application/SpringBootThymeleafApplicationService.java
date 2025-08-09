package com.seed4j.generator.server.springboot.thymeleaf.init.application;

import com.seed4j.generator.server.springboot.thymeleaf.init.domain.SpringBootThymeleafModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootThymeleafApplicationService {

  private final SpringBootThymeleafModuleFactory springBootThymeleaf;

  public SpringBootThymeleafApplicationService() {
    springBootThymeleaf = new SpringBootThymeleafModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return springBootThymeleaf.buildModule(properties);
  }
}
