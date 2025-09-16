package com.seed4j.generator.buildtool.maven.application;

import com.seed4j.generator.buildtool.maven.domain.MavenModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class MavenApplicationService {

  private final MavenModuleFactory maven;

  public MavenApplicationService() {
    maven = new MavenModuleFactory();
  }

  public Seed4JModule buildMavenModule(Seed4JModuleProperties properties) {
    return maven.buildMavenModule(properties);
  }

  public Seed4JModule buildMavenWrapperModule(Seed4JModuleProperties properties) {
    return maven.buildMavenWrapperModule(properties);
  }
}
