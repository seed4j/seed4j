package com.seed4j.generator.client.paginationdomain.application;

import com.seed4j.generator.client.paginationdomain.domain.TSPaginationDomainModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class TsPaginationDomainApplicationService {

  private static final TSPaginationDomainModuleFactory factory = new TSPaginationDomainModuleFactory();

  public SeedModule buildModule(SeedModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
