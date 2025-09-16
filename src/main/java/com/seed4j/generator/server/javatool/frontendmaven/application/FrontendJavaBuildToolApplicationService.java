package com.seed4j.generator.server.javatool.frontendmaven.application;

import com.seed4j.generator.server.javatool.frontendmaven.domain.FrontendJavaBuildToolModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class FrontendJavaBuildToolApplicationService {

  private final FrontendJavaBuildToolModuleFactory frontendJavaBuildTool;

  public FrontendJavaBuildToolApplicationService(NodeVersions nodeVersions) {
    frontendJavaBuildTool = new FrontendJavaBuildToolModuleFactory(nodeVersions);
  }

  public Seed4JModule buildFrontendMavenModule(Seed4JModuleProperties properties) {
    return frontendJavaBuildTool.buildFrontendMavenModule(properties);
  }

  public Seed4JModule buildFrontendGradleModule(Seed4JModuleProperties properties) {
    return frontendJavaBuildTool.buildFrontendGradleModule(properties);
  }

  public Seed4JModule buildFrontendMavenCacheModule(Seed4JModuleProperties properties) {
    return frontendJavaBuildTool.buildFrontendMavenCacheModule(properties);
  }

  public Seed4JModule buildMergeCypressCoverageModule(Seed4JModuleProperties properties) {
    return frontendJavaBuildTool.buildMergeCypressCoverageModule(properties);
  }
}
