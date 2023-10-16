package tech.jhipster.lite.generator.client.tools.playwright.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.tools.playwright.domain.PlaywrightModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class PlaywrightApplicationService {

  private final PlaywrightModuleFactory factory;

  public PlaywrightApplicationService() {
    factory = new PlaywrightModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
