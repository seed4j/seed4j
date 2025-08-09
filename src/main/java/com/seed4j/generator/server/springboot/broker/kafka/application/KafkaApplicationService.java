package com.seed4j.generator.server.springboot.broker.kafka.application;

import com.seed4j.generator.server.springboot.broker.kafka.domain.KafkaModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class KafkaApplicationService {

  private final KafkaModuleFactory kafka;

  public KafkaApplicationService(DockerImages dockerImages) {
    this.kafka = new KafkaModuleFactory(dockerImages);
  }

  public SeedModule init(SeedModuleProperties properties) {
    return kafka.buildModuleInit(properties);
  }

  public SeedModule addSampleProducerConsumer(SeedModuleProperties properties) {
    return kafka.buildModuleSampleProducerConsumer(properties);
  }

  public SeedModule addAkhq(SeedModuleProperties properties) {
    return kafka.buildModuleAkhq(properties);
  }
}
