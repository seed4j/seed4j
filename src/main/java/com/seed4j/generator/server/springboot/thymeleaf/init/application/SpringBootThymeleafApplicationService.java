package com.seed4j.generator.server.springboot.thymeleaf.init.application;

import com.seed4j.generator.server.springboot.thymeleaf.init.domain.SpringBootThymeleafModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootThymeleafApplicationService {

  private final SpringBootThymeleafModuleFactory springBootThymeleaf;

  public SpringBootThymeleafApplicationService() {
    springBootThymeleaf = new SpringBootThymeleafModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return springBootThymeleaf.buildModule(properties);
  }
}
