package com.seed4j.generator.client.restpagination.application;

import com.seed4j.generator.client.restpagination.domain.TSRestPaginationModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class TSRestPaginationApplicationService {

  private static final TSRestPaginationModuleFactory factory = new TSRestPaginationModuleFactory();

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
