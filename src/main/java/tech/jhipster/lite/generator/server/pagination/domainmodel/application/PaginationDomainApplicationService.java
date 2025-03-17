package tech.jhipster.lite.generator.server.pagination.domainmodel.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.pagination.domainmodel.domain.PaginationDomainModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class PaginationDomainApplicationService {

  private final PaginationDomainModuleFactory paginationDomain;

  public PaginationDomainApplicationService() {
    paginationDomain = new PaginationDomainModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return paginationDomain.buildModule(properties);
  }
}
