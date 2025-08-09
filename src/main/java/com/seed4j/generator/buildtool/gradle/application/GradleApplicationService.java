package com.seed4j.generator.buildtool.gradle.application;

import com.seed4j.generator.buildtool.gradle.domain.GradleModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class GradleApplicationService {

  private final GradleModuleFactory gradle;

  public GradleApplicationService() {
    gradle = new GradleModuleFactory();
  }

  public SeedModule buildGradleModule(SeedModuleProperties properties) {
    return gradle.buildGradleModule(properties);
  }

  public SeedModule buildGradleWrapperModule(SeedModuleProperties properties) {
    return gradle.buildGradleWrapperModule(properties);
  }
}
