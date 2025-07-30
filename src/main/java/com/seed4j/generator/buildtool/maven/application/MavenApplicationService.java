package com.seed4j.generator.buildtool.maven.application;

import com.seed4j.generator.buildtool.maven.domain.MavenModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class MavenApplicationService {

  private final MavenModuleFactory maven;

  public MavenApplicationService() {
    maven = new MavenModuleFactory();
  }

  public JHipsterModule buildMavenModule(JHipsterModuleProperties properties) {
    return maven.buildMavenModule(properties);
  }

  public JHipsterModule buildMavenWrapperModule(JHipsterModuleProperties properties) {
    return maven.buildMavenWrapperModule(properties);
  }
}
