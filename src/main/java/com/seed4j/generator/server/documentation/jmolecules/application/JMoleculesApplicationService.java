package com.seed4j.generator.server.documentation.jmolecules.application;

import com.seed4j.generator.server.documentation.jmolecules.domain.JMoleculesModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JMoleculesApplicationService {

  private final JMoleculesModuleFactory jMolecules;

  public JMoleculesApplicationService() {
    jMolecules = new JMoleculesModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return jMolecules.buildModule(properties);
  }
}
