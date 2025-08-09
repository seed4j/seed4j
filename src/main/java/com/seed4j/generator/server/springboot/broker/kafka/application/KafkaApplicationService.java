package com.seed4j.generator.server.springboot.broker.kafka.application;

import com.seed4j.generator.server.springboot.broker.kafka.domain.KafkaModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class KafkaApplicationService {

  private final KafkaModuleFactory kafka;

  public KafkaApplicationService(DockerImages dockerImages) {
    this.kafka = new KafkaModuleFactory(dockerImages);
  }

  public JHipsterModule init(SeedModuleProperties properties) {
    return kafka.buildModuleInit(properties);
  }

  public JHipsterModule addSampleProducerConsumer(SeedModuleProperties properties) {
    return kafka.buildModuleSampleProducerConsumer(properties);
  }

  public JHipsterModule addAkhq(SeedModuleProperties properties) {
    return kafka.buildModuleAkhq(properties);
  }
}
