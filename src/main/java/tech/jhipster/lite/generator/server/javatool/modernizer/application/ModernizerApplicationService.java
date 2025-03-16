package tech.jhipster.lite.generator.server.javatool.modernizer.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.javatool.modernizer.domain.ModernizerModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class ModernizerApplicationService {

  private final ModernizerModuleFactory factory;

  public ModernizerApplicationService() {
    factory = new ModernizerModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
