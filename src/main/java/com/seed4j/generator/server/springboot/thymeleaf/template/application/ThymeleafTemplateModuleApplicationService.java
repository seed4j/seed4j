package com.seed4j.generator.server.springboot.thymeleaf.template.application;

import com.seed4j.generator.server.springboot.thymeleaf.template.domain.ThymeleafTemplateModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ThymeleafTemplateModuleApplicationService {

  private final ThymeleafTemplateModuleFactory thymeleafTemplateFactory;

  public ThymeleafTemplateModuleApplicationService() {
    thymeleafTemplateFactory = new ThymeleafTemplateModuleFactory();
  }

  public Seed4JModule buildThymeleafTemplateModule(Seed4JModuleProperties properties) {
    return thymeleafTemplateFactory.buildModule(properties);
  }

  public Seed4JModule buildThymeleafTemplateTailwindcssModule(Seed4JModuleProperties properties) {
    return thymeleafTemplateFactory.buildTailwindcssModule(properties);
  }

  public Seed4JModule buildThymeleafHtmxWebjarsModule(Seed4JModuleProperties properties) {
    return thymeleafTemplateFactory.buildHtmxWebjarsModule(properties);
  }

  public Seed4JModule buildThymeleafAlpinejsWebjarsModule(Seed4JModuleProperties properties) {
    return thymeleafTemplateFactory.buildAlpineWebjarsModule(properties);
  }
}
