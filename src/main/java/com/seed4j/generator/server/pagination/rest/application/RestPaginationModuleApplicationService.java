package com.seed4j.generator.server.pagination.rest.application;

import com.seed4j.generator.server.pagination.rest.domain.RestPaginationModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class RestPaginationModuleApplicationService {

  private final RestPaginationModuleFactory restPagination;

  public RestPaginationModuleApplicationService() {
    restPagination = new RestPaginationModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return restPagination.buildModule(properties);
  }
}
