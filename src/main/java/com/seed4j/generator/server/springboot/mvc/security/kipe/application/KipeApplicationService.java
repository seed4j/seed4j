package com.seed4j.generator.server.springboot.mvc.security.kipe.application;

import com.seed4j.generator.server.springboot.mvc.security.kipe.domain.KipeAuthorizationModuleFactory;
import com.seed4j.generator.server.springboot.mvc.security.kipe.domain.KipeExpressionModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class KipeApplicationService {

  private final KipeExpressionModuleFactory expressions;
  private final KipeAuthorizationModuleFactory authorizations;

  public KipeApplicationService() {
    expressions = new KipeExpressionModuleFactory();
    authorizations = new KipeAuthorizationModuleFactory();
  }

  public SeedModule buildKipeExpressions(SeedModuleProperties properties) {
    return expressions.buildModule(properties);
  }

  public SeedModule buildKipeAuthorizations(SeedModuleProperties properties) {
    return authorizations.buildModule(properties);
  }
}
