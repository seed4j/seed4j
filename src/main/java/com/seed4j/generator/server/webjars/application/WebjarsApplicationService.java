package com.seed4j.generator.server.webjars.application;

import com.seed4j.generator.server.webjars.domain.WebjarsModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class WebjarsApplicationService {

  private final WebjarsModuleFactory webjars;

  public WebjarsApplicationService() {
    webjars = new WebjarsModuleFactory();
  }

  public Seed4JModule buildWebjarsLocatorModule(Seed4JModuleProperties properties) {
    return webjars.buildWebjarsLocatorModule(properties);
  }

  public Seed4JModule buildWebjarsHtmxModule(Seed4JModuleProperties properties) {
    return webjars.buildWebjarsHtmxModule(properties);
  }

  public Seed4JModule buildWebjarsAlpineJSModule(Seed4JModuleProperties properties) {
    return webjars.buildWebjarsAlpineJSModule(properties);
  }
}
