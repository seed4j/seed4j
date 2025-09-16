package com.seed4j.generator.server.javatool.approvaltesting.application;

import com.seed4j.generator.server.javatool.approvaltesting.domain.ApprovalTestingModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ApprovalTestingApplicationService {

  private final ApprovalTestingModuleFactory approvalTesting;

  public ApprovalTestingApplicationService() {
    approvalTesting = new ApprovalTestingModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return approvalTesting.buildModule(properties);
  }
}
