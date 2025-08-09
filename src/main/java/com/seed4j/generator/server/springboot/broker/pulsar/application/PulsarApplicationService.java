package com.seed4j.generator.server.springboot.broker.pulsar.application;

import com.seed4j.generator.server.springboot.broker.pulsar.domain.PulsarModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class PulsarApplicationService {

  private final PulsarModuleFactory pulsar;

  public PulsarApplicationService(DockerImages dockerImages) {
    pulsar = new PulsarModuleFactory(dockerImages);
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return pulsar.buildModule(properties);
  }
}
