package com.seed4j.generator.server.springboot.thymeleaf.template.application;

import com.seed4j.generator.server.springboot.thymeleaf.template.domain.ThymeleafTemplateModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ThymeleafTemplateModuleApplicationService {

  private final ThymeleafTemplateModuleFactory thymeleafTemplateFactory;

  public ThymeleafTemplateModuleApplicationService() {
    thymeleafTemplateFactory = new ThymeleafTemplateModuleFactory();
  }

  public SeedModule buildThymeleafTemplateModule(SeedModuleProperties properties) {
    return thymeleafTemplateFactory.buildModule(properties);
  }

  public SeedModule buildThymeleafTemplateTailwindcssModule(SeedModuleProperties properties) {
    return thymeleafTemplateFactory.buildTailwindcssModule(properties);
  }

  public SeedModule buildThymeleafHtmxWebjarsModule(SeedModuleProperties properties) {
    return thymeleafTemplateFactory.buildHtmxWebjarsModule(properties);
  }

  public SeedModule buildThymeleafAlpinejsWebjarsModule(SeedModuleProperties properties) {
    return thymeleafTemplateFactory.buildAlpineWebjarsModule(properties);
  }
}
