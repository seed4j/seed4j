package com.seed4j.generator.server.springboot.mvc.sample.feature.application;

import com.seed4j.generator.server.springboot.mvc.sample.feature.domain.SampleFeatureModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SampleFeatureApplicationService {

  private final SampleFeatureModuleFactory sampleFeature;

  public SampleFeatureApplicationService() {
    sampleFeature = new SampleFeatureModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return sampleFeature.buildModule(properties);
  }
}
