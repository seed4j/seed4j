package com.seed4j.generator.server.springboot.mvc.security.kipe.application;

import com.seed4j.generator.server.springboot.mvc.security.kipe.domain.KipeAuthorizationModuleFactory;
import com.seed4j.generator.server.springboot.mvc.security.kipe.domain.KipeExpressionModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class KipeApplicationService {

  private final KipeExpressionModuleFactory expressions;
  private final KipeAuthorizationModuleFactory authorizations;

  public KipeApplicationService() {
    expressions = new KipeExpressionModuleFactory();
    authorizations = new KipeAuthorizationModuleFactory();
  }

  public Seed4JModule buildKipeExpressions(Seed4JModuleProperties properties) {
    return expressions.buildModule(properties);
  }

  public Seed4JModule buildKipeAuthorizations(Seed4JModuleProperties properties) {
    return authorizations.buildModule(properties);
  }
}
