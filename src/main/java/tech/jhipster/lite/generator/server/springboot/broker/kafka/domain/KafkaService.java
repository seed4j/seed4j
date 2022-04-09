package tech.jhipster.lite.generator.server.springboot.broker.kafka.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface KafkaService {
  void init(Project project);

  void addDummyProducer(Project project);

  void addAkhq(Project project);
}
