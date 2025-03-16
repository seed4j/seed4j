package tech.jhipster.lite.generator.server.webjars.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.webjars.domain.WebjarsModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class WebjarsApplicationService {

  private final WebjarsModuleFactory factory;

  public WebjarsApplicationService() {
    factory = new WebjarsModuleFactory();
  }

  public JHipsterModule buildWebjarsLocatorModule(JHipsterModuleProperties properties) {
    return factory.buildWebjarsLocatorModule(properties);
  }

  public JHipsterModule buildWebjarsHtmxModule(JHipsterModuleProperties properties) {
    return factory.buildWebjarsHtmxModule(properties);
  }

  public JHipsterModule buildWebjarsAlpineJSModule(JHipsterModuleProperties properties) {
    return factory.buildWebjarsAlpineJSModule(properties);
  }
}
