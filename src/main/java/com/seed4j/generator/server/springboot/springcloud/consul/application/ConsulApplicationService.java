package com.seed4j.generator.server.springboot.springcloud.consul.application;

import com.seed4j.generator.server.springboot.springcloud.consul.domain.ConsulModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ConsulApplicationService {

  private final ConsulModuleFactory consul;

  public ConsulApplicationService(DockerImages dockerImages) {
    this.consul = new ConsulModuleFactory(dockerImages);
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return consul.buildModule(properties);
  }
}
