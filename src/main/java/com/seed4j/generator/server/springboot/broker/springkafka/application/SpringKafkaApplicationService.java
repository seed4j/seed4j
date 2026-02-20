package com.seed4j.generator.server.springboot.broker.springkafka.application;

import com.seed4j.generator.server.springboot.broker.springkafka.domain.SpringKafkaModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringKafkaApplicationService {

  private final SpringKafkaModuleFactory springKafka;

  public SpringKafkaApplicationService(DockerImages dockerImages) {
    this.springKafka = new SpringKafkaModuleFactory(dockerImages);
  }

  public Seed4JModule init(Seed4JModuleProperties properties) {
    return springKafka.buildModuleInit(properties);
  }

  public Seed4JModule addSampleProducerConsumer(Seed4JModuleProperties properties) {
    return springKafka.buildModuleSampleProducerConsumer(properties);
  }

  public Seed4JModule addAkhq(Seed4JModuleProperties properties) {
    return springKafka.buildModuleAkhq(properties);
  }
}
