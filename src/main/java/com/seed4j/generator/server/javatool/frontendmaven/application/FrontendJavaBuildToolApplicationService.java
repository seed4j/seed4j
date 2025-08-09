package com.seed4j.generator.server.javatool.frontendmaven.application;

import com.seed4j.generator.server.javatool.frontendmaven.domain.FrontendJavaBuildToolModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class FrontendJavaBuildToolApplicationService {

  private final FrontendJavaBuildToolModuleFactory frontendJavaBuildTool;

  public FrontendJavaBuildToolApplicationService(NodeVersions nodeVersions) {
    frontendJavaBuildTool = new FrontendJavaBuildToolModuleFactory(nodeVersions);
  }

  public JHipsterModule buildFrontendMavenModule(SeedModuleProperties properties) {
    return frontendJavaBuildTool.buildFrontendMavenModule(properties);
  }

  public JHipsterModule buildFrontendGradleModule(SeedModuleProperties properties) {
    return frontendJavaBuildTool.buildFrontendGradleModule(properties);
  }

  public JHipsterModule buildFrontendMavenCacheModule(SeedModuleProperties properties) {
    return frontendJavaBuildTool.buildFrontendMavenCacheModule(properties);
  }

  public JHipsterModule buildMergeCypressCoverageModule(SeedModuleProperties properties) {
    return frontendJavaBuildTool.buildMergeCypressCoverageModule(properties);
  }
}
