package com.seed4j.generator.server.springboot.broker.kafka.application;

import com.seed4j.generator.server.springboot.broker.kafka.domain.KafkaModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class KafkaApplicationService {

  private final KafkaModuleFactory kafka;

  public KafkaApplicationService(DockerImages dockerImages) {
    this.kafka = new KafkaModuleFactory(dockerImages);
  }

  public Seed4JModule init(Seed4JModuleProperties properties) {
    return kafka.buildModuleInit(properties);
  }

  public Seed4JModule addSampleProducerConsumer(Seed4JModuleProperties properties) {
    return kafka.buildModuleSampleProducerConsumer(properties);
  }

  public Seed4JModule addAkhq(Seed4JModuleProperties properties) {
    return kafka.buildModuleAkhq(properties);
  }
}
