package tech.jhipster.lite.generator.server.pagination.rest.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.pagination.rest.domain.RestPaginationModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

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
