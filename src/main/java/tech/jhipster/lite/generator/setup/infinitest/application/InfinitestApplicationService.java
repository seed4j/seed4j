package tech.jhipster.lite.generator.setup.infinitest.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.setup.infinitest.domain.InfinitestModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class InfinitestApplicationService {

  private final InfinitestModuleFactory infinitest;

  public InfinitestApplicationService() {
    infinitest = new InfinitestModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return infinitest.buildModule(properties);
  }
}
