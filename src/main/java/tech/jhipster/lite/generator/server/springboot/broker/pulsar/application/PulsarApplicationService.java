package tech.jhipster.lite.generator.server.springboot.broker.pulsar.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.broker.pulsar.domain.PulsarModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class PulsarApplicationService {

  private final PulsarModuleFactory pulsar;

  public PulsarApplicationService(DockerImages dockerImages) {
    pulsar = new PulsarModuleFactory(dockerImages);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return pulsar.buildModule(properties);
  }
}
