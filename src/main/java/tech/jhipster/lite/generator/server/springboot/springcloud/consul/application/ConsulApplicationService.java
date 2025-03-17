package tech.jhipster.lite.generator.server.springboot.springcloud.consul.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain.ConsulModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class ConsulApplicationService {

  private final ConsulModuleFactory consul;

  public ConsulApplicationService(DockerImages dockerImages) {
    this.consul = new ConsulModuleFactory(dockerImages);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return consul.buildModule(properties);
  }
}
