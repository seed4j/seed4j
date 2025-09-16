package com.seed4j.generator.client.tools.cypressmergecoverage.application;

import com.seed4j.generator.client.tools.cypressmergecoverage.domain.CypressMergeCoverageModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CypressMergeCoverageApplicationService {

  private final CypressMergeCoverageModuleFactory cypressMergeCoverage;

  public CypressMergeCoverageApplicationService() {
    cypressMergeCoverage = new CypressMergeCoverageModuleFactory();
  }

  public Seed4JModule buildCypressMergeCoverage(Seed4JModuleProperties properties) {
    return cypressMergeCoverage.buildCypressMergeCoverage(properties);
  }
}
