package tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.domain.SpringBootActuatorModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringBootActuatorApplicationService {

  private final SpringBootActuatorModuleFactory factory;

  public SpringBootActuatorApplicationService() {
    factory = new SpringBootActuatorModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
