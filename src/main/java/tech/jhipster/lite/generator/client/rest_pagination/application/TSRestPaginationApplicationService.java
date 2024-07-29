package tech.jhipster.lite.generator.client.rest_pagination.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.rest_pagination.domain.TSRestPaginationFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class TSRestPaginationApplicationService {

  private static final TSRestPaginationFactory factory = new TSRestPaginationFactory();

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
