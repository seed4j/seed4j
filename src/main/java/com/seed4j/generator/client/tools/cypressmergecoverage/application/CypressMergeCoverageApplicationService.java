package com.seed4j.generator.client.tools.cypressmergecoverage.application;

import com.seed4j.generator.client.tools.cypressmergecoverage.domain.CypressMergeCoverageModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CypressMergeCoverageApplicationService {

  private final CypressMergeCoverageModuleFactory cypressMergeCoverage;

  public CypressMergeCoverageApplicationService() {
    cypressMergeCoverage = new CypressMergeCoverageModuleFactory();
  }

  public JHipsterModule buildCypressMergeCoverage(SeedModuleProperties properties) {
    return cypressMergeCoverage.buildCypressMergeCoverage(properties);
  }
}
