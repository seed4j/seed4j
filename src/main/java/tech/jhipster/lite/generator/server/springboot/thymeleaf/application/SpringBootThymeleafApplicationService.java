package tech.jhipster.lite.generator.server.springboot.thymeleaf.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.thymeleaf.domain.SpringBootThymeleafModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringBootThymeleafApplicationService {

  private final SpringBootThymeleafModuleFactory factory;

  public SpringBootThymeleafApplicationService() {
    factory = new SpringBootThymeleafModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
