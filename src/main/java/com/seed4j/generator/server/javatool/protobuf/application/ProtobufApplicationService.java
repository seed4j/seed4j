package com.seed4j.generator.server.javatool.protobuf.application;

import com.seed4j.generator.server.javatool.protobuf.domain.ProtobufModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ProtobufApplicationService {

  private final ProtobufModuleFactory protobuf;

  public ProtobufApplicationService() {
    protobuf = new ProtobufModuleFactory();
  }

  public Seed4JModule buildProtobufModule(Seed4JModuleProperties properties) {
    return protobuf.buildProtobufModule(properties);
  }

  public Seed4JModule buildProtobufBackwardsCompatibilityCheckModule(Seed4JModuleProperties properties) {
    return protobuf.buildProtobufBackwardsCompatibilityCheckModule(properties);
  }
}
