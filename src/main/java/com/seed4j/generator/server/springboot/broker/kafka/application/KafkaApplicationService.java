package com.seed4j.generator.server.springboot.broker.kafka.application;

import com.seed4j.generator.server.springboot.broker.kafka.domain.KafkaModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class KafkaApplicationService {

  private final KafkaModuleFactory kafka;

  public KafkaApplicationService(DockerImages dockerImages) {
    this.kafka = new KafkaModuleFactory(dockerImages);
  }

  public JHipsterModule init(JHipsterModuleProperties properties) {
    return kafka.buildModuleInit(properties);
  }

  public JHipsterModule addSampleProducerConsumer(JHipsterModuleProperties properties) {
    return kafka.buildModuleSampleProducerConsumer(properties);
  }

  public JHipsterModule addAkhq(JHipsterModuleProperties properties) {
    return kafka.buildModuleAkhq(properties);
  }
}
