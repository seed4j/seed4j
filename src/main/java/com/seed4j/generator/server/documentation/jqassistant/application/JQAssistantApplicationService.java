package com.seed4j.generator.server.documentation.jqassistant.application;

import com.seed4j.generator.server.documentation.jqassistant.domain.JQAssistantModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JQAssistantApplicationService {

  private final JQAssistantModuleFactory jqAssistant;

  public JQAssistantApplicationService() {
    jqAssistant = new JQAssistantModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return jqAssistant.buildModule(properties);
  }

  public JHipsterModule buildJMoleculesModule(JHipsterModuleProperties properties) {
    return jqAssistant.buildJMoleculesModule(properties);
  }

  public JHipsterModule buildSpringModule(JHipsterModuleProperties properties) {
    return jqAssistant.buildSpringModule(properties);
  }
}
