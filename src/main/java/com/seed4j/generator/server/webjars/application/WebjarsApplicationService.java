package com.seed4j.generator.server.webjars.application;

import com.seed4j.generator.server.webjars.domain.WebjarsModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class WebjarsApplicationService {

  private final WebjarsModuleFactory webjars;

  public WebjarsApplicationService() {
    webjars = new WebjarsModuleFactory();
  }

  public SeedModule buildWebjarsLocatorModule(SeedModuleProperties properties) {
    return webjars.buildWebjarsLocatorModule(properties);
  }

  public SeedModule buildWebjarsHtmxModule(SeedModuleProperties properties) {
    return webjars.buildWebjarsHtmxModule(properties);
  }

  public SeedModule buildWebjarsAlpineJSModule(SeedModuleProperties properties) {
    return webjars.buildWebjarsAlpineJSModule(properties);
  }
}
