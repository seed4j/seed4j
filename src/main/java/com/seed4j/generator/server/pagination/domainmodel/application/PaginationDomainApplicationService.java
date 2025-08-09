package com.seed4j.generator.server.pagination.domainmodel.application;

import com.seed4j.generator.server.pagination.domainmodel.domain.PaginationDomainModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class PaginationDomainApplicationService {

  private final PaginationDomainModuleFactory paginationDomain;

  public PaginationDomainApplicationService() {
    paginationDomain = new PaginationDomainModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return paginationDomain.buildModule(properties);
  }
}
