package com.seed4j.generator.server.pagination.jpa.application;

import com.seed4j.generator.server.pagination.jpa.domain.JpaPaginationModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JpaPaginationModuleApplicationService {

  private final JpaPaginationModuleFactory jpaPagination;

  public JpaPaginationModuleApplicationService() {
    jpaPagination = new JpaPaginationModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return jpaPagination.buildModule(properties);
  }
}
