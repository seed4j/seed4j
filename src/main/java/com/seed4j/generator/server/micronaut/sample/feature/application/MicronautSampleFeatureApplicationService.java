package com.seed4j.generator.server.micronaut.sample.feature.application;

import com.seed4j.generator.server.micronaut.sample.feature.domain.MicronautSampleFeatureModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class MicronautSampleFeatureApplicationService {

  private final MicronautSampleFeatureModuleFactory factory;

  public MicronautSampleFeatureApplicationService() {
    this.factory = new MicronautSampleFeatureModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
