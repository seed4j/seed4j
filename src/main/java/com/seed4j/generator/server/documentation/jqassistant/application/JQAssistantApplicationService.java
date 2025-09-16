package com.seed4j.generator.server.documentation.jqassistant.application;

import com.seed4j.generator.server.documentation.jqassistant.domain.JQAssistantModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JQAssistantApplicationService {

  private final JQAssistantModuleFactory jqAssistant;

  public JQAssistantApplicationService() {
    jqAssistant = new JQAssistantModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return jqAssistant.buildModule(properties);
  }

  public Seed4JModule buildJMoleculesModule(Seed4JModuleProperties properties) {
    return jqAssistant.buildJMoleculesModule(properties);
  }

  public Seed4JModule buildSpringModule(Seed4JModuleProperties properties) {
    return jqAssistant.buildSpringModule(properties);
  }
}
