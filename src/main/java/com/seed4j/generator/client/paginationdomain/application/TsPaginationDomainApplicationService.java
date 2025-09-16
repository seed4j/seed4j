package com.seed4j.generator.client.paginationdomain.application;

import com.seed4j.generator.client.paginationdomain.domain.TSPaginationDomainModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class TsPaginationDomainApplicationService {

  private static final TSPaginationDomainModuleFactory factory = new TSPaginationDomainModuleFactory();

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
