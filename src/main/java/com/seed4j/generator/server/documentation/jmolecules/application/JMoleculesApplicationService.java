package com.seed4j.generator.server.documentation.jmolecules.application;

import com.seed4j.generator.server.documentation.jmolecules.domain.JMoleculesModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JMoleculesApplicationService {

  private final JMoleculesModuleFactory jMolecules;

  public JMoleculesApplicationService() {
    jMolecules = new JMoleculesModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return jMolecules.buildModule(properties);
  }
}
