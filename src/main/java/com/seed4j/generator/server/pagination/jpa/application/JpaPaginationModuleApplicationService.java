package com.seed4j.generator.server.pagination.jpa.application;

import com.seed4j.generator.server.pagination.jpa.domain.JpaPaginationModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class JpaPaginationModuleApplicationService {

  private final JpaPaginationModuleFactory jpaPagination;

  public JpaPaginationModuleApplicationService() {
    jpaPagination = new JpaPaginationModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return jpaPagination.buildModule(properties);
  }
}
