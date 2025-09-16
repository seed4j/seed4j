package com.seed4j.generator.client.restpagination.application;

import com.seed4j.generator.client.restpagination.domain.TSRestPaginationModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class TSRestPaginationApplicationService {

  private static final TSRestPaginationModuleFactory factory = new TSRestPaginationModuleFactory();

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
