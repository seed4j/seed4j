package tech.jhipster.lite.generator.server.springboot.broker.kafka.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.KafkaService;

@Service
public record KafkaApplicationService(KafkaService kafkaService) {
  public void init(final Project project) {
    kafkaService.init(project);
  }

  public void addDummyProducer(final Project project) {
    kafkaService.addDummyProducer(project);
  }

  public void addAkhq(final Project project) {
    kafkaService.addAkhq(project);
  }
}
