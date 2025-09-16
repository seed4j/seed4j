package com.seed4j.generator.server.springboot.springcloud.gateway.application;

import com.seed4j.generator.server.springboot.springcloud.gateway.domain.GatewayModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class GatewayApplicationService {

  private final GatewayModuleFactory gateway;

  public GatewayApplicationService() {
    gateway = new GatewayModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return gateway.buildModule(properties);
  }
}
