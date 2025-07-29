package com.seed4j.generator.client.paginationdomain.application;

import com.seed4j.generator.client.paginationdomain.domain.TSPaginationDomainModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class TsPaginationDomainApplicationService {

  private static final TSPaginationDomainModuleFactory factory = new TSPaginationDomainModuleFactory();

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
