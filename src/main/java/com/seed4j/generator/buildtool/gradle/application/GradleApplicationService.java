package com.seed4j.generator.buildtool.gradle.application;

import com.seed4j.generator.buildtool.gradle.domain.GradleModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class GradleApplicationService {

  private final GradleModuleFactory gradle;

  public GradleApplicationService() {
    gradle = new GradleModuleFactory();
  }

  public JHipsterModule buildGradleModule(SeedModuleProperties properties) {
    return gradle.buildGradleModule(properties);
  }

  public JHipsterModule buildGradleWrapperModule(SeedModuleProperties properties) {
    return gradle.buildGradleWrapperModule(properties);
  }
}
