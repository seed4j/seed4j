package com.seed4j.generator.server.springboot.springcloud.gateway.application;

import com.seed4j.generator.server.springboot.springcloud.gateway.domain.GatewayModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class GatewayApplicationService {

  private final GatewayModuleFactory gateway;

  public GatewayApplicationService() {
    gateway = new GatewayModuleFactory();
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return gateway.buildModule(properties);
  }
}
