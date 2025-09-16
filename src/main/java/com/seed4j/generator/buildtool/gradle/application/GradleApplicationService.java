package com.seed4j.generator.buildtool.gradle.application;

import com.seed4j.generator.buildtool.gradle.domain.GradleModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class GradleApplicationService {

  private final GradleModuleFactory gradle;

  public GradleApplicationService() {
    gradle = new GradleModuleFactory();
  }

  public Seed4JModule buildGradleModule(Seed4JModuleProperties properties) {
    return gradle.buildGradleModule(properties);
  }

  public Seed4JModule buildGradleWrapperModule(Seed4JModuleProperties properties) {
    return gradle.buildGradleWrapperModule(properties);
  }
}
