package tech.jhipster.lite.generator.enums.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.enums.domain.EnumModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class EnumApplicationService {

  private final EnumModuleFactory factory;

  public EnumApplicationService() {
    factory = new EnumModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
