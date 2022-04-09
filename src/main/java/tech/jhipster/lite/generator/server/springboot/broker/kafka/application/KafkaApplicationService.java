package tech.jhipster.lite.generator.server.springboot.broker.kafka.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.KafkaService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class KafkaApplicationService {

  private final KafkaService kafkaService;

  public KafkaApplicationService(final KafkaService kafkaService) {
    this.kafkaService = kafkaService;
  }

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
