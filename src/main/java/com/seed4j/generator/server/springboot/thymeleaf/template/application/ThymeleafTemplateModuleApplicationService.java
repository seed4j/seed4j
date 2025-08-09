package com.seed4j.generator.server.springboot.thymeleaf.template.application;

import com.seed4j.generator.server.springboot.thymeleaf.template.domain.ThymeleafTemplateModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ThymeleafTemplateModuleApplicationService {

  private final ThymeleafTemplateModuleFactory thymeleafTemplateFactory;

  public ThymeleafTemplateModuleApplicationService() {
    thymeleafTemplateFactory = new ThymeleafTemplateModuleFactory();
  }

  public JHipsterModule buildThymeleafTemplateModule(SeedModuleProperties properties) {
    return thymeleafTemplateFactory.buildModule(properties);
  }

  public JHipsterModule buildThymeleafTemplateTailwindcssModule(SeedModuleProperties properties) {
    return thymeleafTemplateFactory.buildTailwindcssModule(properties);
  }

  public JHipsterModule buildThymeleafHtmxWebjarsModule(SeedModuleProperties properties) {
    return thymeleafTemplateFactory.buildHtmxWebjarsModule(properties);
  }

  public JHipsterModule buildThymeleafAlpinejsWebjarsModule(SeedModuleProperties properties) {
    return thymeleafTemplateFactory.buildAlpineWebjarsModule(properties);
  }
}
