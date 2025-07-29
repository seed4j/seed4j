package com.seed4j.generator.server.javatool.protobuf.application;

import com.seed4j.generator.server.javatool.protobuf.domain.ProtobufModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

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
