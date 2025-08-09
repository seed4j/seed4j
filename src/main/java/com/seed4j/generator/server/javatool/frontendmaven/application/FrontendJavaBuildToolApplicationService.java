package com.seed4j.generator.server.javatool.frontendmaven.application;

import com.seed4j.generator.server.javatool.frontendmaven.domain.FrontendJavaBuildToolModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class FrontendJavaBuildToolApplicationService {

  private final FrontendJavaBuildToolModuleFactory frontendJavaBuildTool;

  public FrontendJavaBuildToolApplicationService(NodeVersions nodeVersions) {
    frontendJavaBuildTool = new FrontendJavaBuildToolModuleFactory(nodeVersions);
  }

  public SeedModule buildFrontendMavenModule(SeedModuleProperties properties) {
    return frontendJavaBuildTool.buildFrontendMavenModule(properties);
  }

  public SeedModule buildFrontendGradleModule(SeedModuleProperties properties) {
    return frontendJavaBuildTool.buildFrontendGradleModule(properties);
  }

  public SeedModule buildFrontendMavenCacheModule(SeedModuleProperties properties) {
    return frontendJavaBuildTool.buildFrontendMavenCacheModule(properties);
  }

  public SeedModule buildMergeCypressCoverageModule(SeedModuleProperties properties) {
    return frontendJavaBuildTool.buildMergeCypressCoverageModule(properties);
  }
}
