package com.seed4j.generator.client.tools.cypressmergecoverage.application;

import com.seed4j.generator.client.tools.cypressmergecoverage.domain.CypressMergeCoverageModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class CypressMergeCoverageApplicationService {

  private final CypressMergeCoverageModuleFactory cypressMergeCoverage;

  public CypressMergeCoverageApplicationService() {
    cypressMergeCoverage = new CypressMergeCoverageModuleFactory();
  }

  public SeedModule buildCypressMergeCoverage(SeedModuleProperties properties) {
    return cypressMergeCoverage.buildCypressMergeCoverage(properties);
  }
}
