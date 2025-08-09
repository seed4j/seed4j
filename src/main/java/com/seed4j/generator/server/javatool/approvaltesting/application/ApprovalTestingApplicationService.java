package com.seed4j.generator.server.javatool.approvaltesting.application;

import com.seed4j.generator.server.javatool.approvaltesting.domain.ApprovalTestingModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ApprovalTestingApplicationService {

  private final ApprovalTestingModuleFactory approvalTesting;

  public ApprovalTestingApplicationService() {
    approvalTesting = new ApprovalTestingModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return approvalTesting.buildModule(properties);
  }
}
