package tech.jhipster.lite.generator.server.springboot.thymeleaf.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.thymeleaf.domain.SpringBootThymeleafModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringBootThymeleafApplicationService {

  private final SpringBootThymeleafModuleFactory springBootThymeleafFactory;

  public SpringBootThymeleafApplicationService() {
    springBootThymeleafFactory = new SpringBootThymeleafModuleFactory();
  }

  public JHipsterModule buildSpringBootThymeleafModule(JHipsterModuleProperties properties) {
    return springBootThymeleafFactory.buildModule(properties);
  }
}
