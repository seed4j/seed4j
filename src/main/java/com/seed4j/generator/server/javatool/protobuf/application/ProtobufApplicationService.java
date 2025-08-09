package com.seed4j.generator.server.javatool.protobuf.application;

import com.seed4j.generator.server.javatool.protobuf.domain.ProtobufModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class ProtobufApplicationService {

  private final ProtobufModuleFactory protobuf;

  public ProtobufApplicationService() {
    protobuf = new ProtobufModuleFactory();
  }

  public SeedModule buildProtobufModule(SeedModuleProperties properties) {
    return protobuf.buildProtobufModule(properties);
  }

  public SeedModule buildProtobufBackwardsCompatibilityCheckModule(SeedModuleProperties properties) {
    return protobuf.buildProtobufBackwardsCompatibilityCheckModule(properties);
  }
}
