package com.seed4j.generator.server.springboot.apidocumentation.openapicontract.application;

import com.seed4j.generator.server.springboot.apidocumentation.openapicontract.domain.OpenApiContractModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class OpenApiContractApplicationService {

  private final OpenApiContractModuleFactory openApiContract;

  public OpenApiContractApplicationService() {
    openApiContract = new OpenApiContractModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return openApiContract.buildModule(properties);
  }

  public Seed4JModule buildBackwardsCompatibilityCheckModule(Seed4JModuleProperties properties) {
    return openApiContract.buildBackwardsCompatibilityCheckModule(properties);
  }
}
