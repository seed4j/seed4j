package com.seed4j.generator.client.tools.playwright.application;

import com.seed4j.generator.client.tools.playwright.domain.PlaywrightModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

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
