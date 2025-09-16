package com.seed4j.generator.server.pagination.domainmodel.application;

import com.seed4j.generator.server.pagination.domainmodel.domain.PaginationDomainModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class PaginationDomainApplicationService {

  private final PaginationDomainModuleFactory paginationDomain;

  public PaginationDomainApplicationService() {
    paginationDomain = new PaginationDomainModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return paginationDomain.buildModule(properties);
  }
}
