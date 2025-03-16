package tech.jhipster.lite.generator.ci.renovate.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.ci.renovate.domain.RenovateModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class RenovateApplicationService {

  private final RenovateModuleFactory renovate;

  public RenovateApplicationService() {
    renovate = new RenovateModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return renovate.buildModule(properties);
  }
}
