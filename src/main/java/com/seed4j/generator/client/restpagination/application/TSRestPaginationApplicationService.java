package com.seed4j.generator.client.restpagination.application;

import com.seed4j.generator.client.restpagination.domain.TSRestPaginationModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class TSRestPaginationApplicationService {

  private static final TSRestPaginationModuleFactory factory = new TSRestPaginationModuleFactory();

  public SeedModule buildModule(SeedModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
