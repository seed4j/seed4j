package com.seed4j.generator.server.pagination.rest.application;

import com.seed4j.generator.server.pagination.rest.domain.RestPaginationModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class RestPaginationModuleApplicationService {

  private final RestPaginationModuleFactory restPagination;

  public RestPaginationModuleApplicationService() {
    restPagination = new RestPaginationModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return restPagination.buildModule(properties);
  }
}
