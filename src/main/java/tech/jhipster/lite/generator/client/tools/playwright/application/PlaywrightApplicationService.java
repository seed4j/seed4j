package tech.jhipster.lite.generator.client.tools.playwright.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.tools.playwright.domain.PlaywrightModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class PlaywrightApplicationService {

  private final PlaywrightModuleFactory playwright;

  public PlaywrightApplicationService() {
    playwright = new PlaywrightModuleFactory();
  }

  public JHipsterModule buildComponentTestsModule(JHipsterModuleProperties properties) {
    return playwright.buildComponentTestsModule(properties);
  }

  public JHipsterModule buildE2ETestsModule(JHipsterModuleProperties properties) {
    return playwright.buildE2ETestsModule(properties);
  }
}
