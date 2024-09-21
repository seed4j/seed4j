package tech.jhipster.lite.generator.client.tikui.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.tikui.domain.TikuiModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class TikuiApplicationService {

  private final TikuiModuleFactory factory = new TikuiModuleFactory();

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
