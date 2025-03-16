package tech.jhipster.lite.generator.server.springboot.thymeleaf.template.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.thymeleaf.template.domain.ThymeleafTemplateModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class ThymeleafTemplateModuleApplicationService {

  private final ThymeleafTemplateModuleFactory factory;

  public ThymeleafTemplateModuleApplicationService() {
    factory = new ThymeleafTemplateModuleFactory();
  }

  public JHipsterModule buildThymeleafTemplateModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }

  public JHipsterModule buildThymeleafTemplateTailwindcssModule(JHipsterModuleProperties properties) {
    return factory.buildTailwindcssModule(properties);
  }

  public JHipsterModule buildThymeleafHtmxWebjarsModule(JHipsterModuleProperties properties) {
    return factory.buildHtmxWebjarsModule(properties);
  }

  public JHipsterModule buildThymeleafAlpinejsWebjarsModule(JHipsterModuleProperties properties) {
    return factory.buildAlpineWebjarsModule(properties);
  }
}
