package tech.jhipster.lite.generator.server.springboot.mvc.security.kipe.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.security.kipe.domain.KipeExpressionModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class KipeApplicationService {

  private final KipeExpressionModuleFactory expressions;

  public KipeApplicationService() {
    expressions = new KipeExpressionModuleFactory();
  }

  public JHipsterModule buildKipeExpressions(JHipsterModuleProperties properties) {
    return expressions.buildModule(properties);
  }
}
