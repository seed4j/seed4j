package com.seed4j.generator.server.springboot.springcloud.eureka.application;

import com.seed4j.generator.server.springboot.springcloud.eureka.domain.EurekaModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class EurekaApplicationService {

  private final EurekaModuleFactory eureka;

  public EurekaApplicationService(DockerImages dockerImages) {
    eureka = new EurekaModuleFactory(dockerImages);
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    return eureka.buildModule(properties);
  }
}
