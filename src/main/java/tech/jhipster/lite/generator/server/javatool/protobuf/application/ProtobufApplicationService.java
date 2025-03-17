package tech.jhipster.lite.generator.server.javatool.protobuf.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.javatool.protobuf.domain.ProtobufModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class ProtobufApplicationService {

  private final ProtobufModuleFactory protobuf;

  public ProtobufApplicationService() {
    protobuf = new ProtobufModuleFactory();
  }

  public JHipsterModule buildProtobufModule(JHipsterModuleProperties properties) {
    return protobuf.buildProtobufModule(properties);
  }

  public JHipsterModule buildProtobufBackwardsCompatibilityCheckModule(JHipsterModuleProperties properties) {
    return protobuf.buildProtobufBackwardsCompatibilityCheckModule(properties);
  }
}
