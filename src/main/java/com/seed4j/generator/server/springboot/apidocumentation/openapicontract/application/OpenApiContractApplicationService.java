package com.seed4j.generator.server.springboot.apidocumentation.openapicontract.application;

import com.seed4j.generator.server.springboot.apidocumentation.openapicontract.domain.OpenApiContractModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class OpenApiContractApplicationService {

  private final OpenApiContractModuleFactory openApiContract;

  public OpenApiContractApplicationService() {
    openApiContract = new OpenApiContractModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return openApiContract.buildModule(properties);
  }

  public SeedModule buildBackwardsCompatibilityCheckModule(SeedModuleProperties properties) {
    return openApiContract.buildBackwardsCompatibilityCheckModule(properties);
  }
}
