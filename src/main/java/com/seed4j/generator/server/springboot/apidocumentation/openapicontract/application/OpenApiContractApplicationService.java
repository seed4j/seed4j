package com.seed4j.generator.server.springboot.apidocumentation.openapicontract.application;

import com.seed4j.generator.server.springboot.apidocumentation.openapicontract.domain.OpenApiContractModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class OpenApiContractApplicationService {

  private final OpenApiContractModuleFactory openApiContract;

  public OpenApiContractApplicationService() {
    openApiContract = new OpenApiContractModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return openApiContract.buildModule(properties);
  }

  public JHipsterModule buildBackwardsCompatibilityCheckModule(JHipsterModuleProperties properties) {
    return openApiContract.buildBackwardsCompatibilityCheckModule(properties);
  }
}
