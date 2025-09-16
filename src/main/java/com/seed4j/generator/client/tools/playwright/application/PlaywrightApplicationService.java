package com.seed4j.generator.client.tools.playwright.application;

import com.seed4j.generator.client.tools.playwright.domain.PlaywrightModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class PlaywrightApplicationService {

  private final PlaywrightModuleFactory playwright;

  public PlaywrightApplicationService() {
    playwright = new PlaywrightModuleFactory();
  }

  public Seed4JModule buildComponentTestsModule(Seed4JModuleProperties properties) {
    return playwright.buildComponentTestsModule(properties);
  }

  public Seed4JModule buildE2ETestsModule(Seed4JModuleProperties properties) {
    return playwright.buildE2ETestsModule(properties);
  }
}
