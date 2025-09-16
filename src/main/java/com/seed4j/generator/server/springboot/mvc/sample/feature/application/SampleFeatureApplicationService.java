package com.seed4j.generator.server.springboot.mvc.sample.feature.application;

import com.seed4j.generator.server.springboot.mvc.sample.feature.domain.SampleFeatureModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SampleFeatureApplicationService {

  private final SampleFeatureModuleFactory sampleFeature;

  public SampleFeatureApplicationService() {
    sampleFeature = new SampleFeatureModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return sampleFeature.buildModule(properties);
  }
}
