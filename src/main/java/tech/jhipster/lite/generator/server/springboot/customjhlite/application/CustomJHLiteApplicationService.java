package tech.jhipster.lite.generator.server.springboot.customjhlite.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.customjhlite.domain.CustomJHLiteModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class CustomJHLiteApplicationService {

  private final CustomJHLiteModuleFactory customJHLite;

  public CustomJHLiteApplicationService() {
    customJHLite = new CustomJHLiteModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return customJHLite.buildModule(properties);
  }
}
