package com.seed4j.generator.server.webjars.application;

import com.seed4j.generator.server.webjars.domain.WebjarsModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class WebjarsApplicationService {

  private final WebjarsModuleFactory webjars;

  public WebjarsApplicationService() {
    webjars = new WebjarsModuleFactory();
  }

  public JHipsterModule buildWebjarsLocatorModule(JHipsterModuleProperties properties) {
    return webjars.buildWebjarsLocatorModule(properties);
  }

  public JHipsterModule buildWebjarsHtmxModule(JHipsterModuleProperties properties) {
    return webjars.buildWebjarsHtmxModule(properties);
  }

  public JHipsterModule buildWebjarsAlpineJSModule(JHipsterModuleProperties properties) {
    return webjars.buildWebjarsAlpineJSModule(properties);
  }
}
