package com.seed4j.generator.server.springboot.mvc.internationalizederrors.application;

import com.seed4j.generator.server.springboot.mvc.internationalizederrors.domain.InternationalizedErrorsModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class InternationalizedErrorsApplicationService {

  private final InternationalizedErrorsModuleFactory internationalizedErrors;

  public InternationalizedErrorsApplicationService() {
    internationalizedErrors = new InternationalizedErrorsModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return internationalizedErrors.buildModule(properties);
  }
}
