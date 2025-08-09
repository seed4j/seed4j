package com.seed4j.generator.server.springboot.mvc.internationalizederrors.application;

import com.seed4j.generator.server.springboot.mvc.internationalizederrors.domain.InternationalizedErrorsModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class InternationalizedErrorsApplicationService {

  private final InternationalizedErrorsModuleFactory internationalizedErrors;

  public InternationalizedErrorsApplicationService() {
    internationalizedErrors = new InternationalizedErrorsModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return internationalizedErrors.buildModule(properties);
  }
}
