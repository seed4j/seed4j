package com.seed4j.generator.server.documentation.jmolecules.application;

import com.seed4j.generator.server.documentation.jmolecules.domain.JMoleculesModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JMoleculesApplicationService {

  private final JMoleculesModuleFactory jMolecules;

  public JMoleculesApplicationService() {
    jMolecules = new JMoleculesModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return jMolecules.buildModule(properties);
  }
}
