package com.seed4j.generator.server.javatool.frontendmaven.application;

import com.seed4j.generator.server.javatool.frontendmaven.domain.FrontendJavaBuildToolModuleFactory;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class FrontendJavaBuildToolApplicationService {

  private final FrontendJavaBuildToolModuleFactory frontendJavaBuildTool;

  public FrontendJavaBuildToolApplicationService(NodeVersions nodeVersions) {
    frontendJavaBuildTool = new FrontendJavaBuildToolModuleFactory(nodeVersions);
  }

  public JHipsterModule buildFrontendMavenModule(JHipsterModuleProperties properties) {
    return frontendJavaBuildTool.buildFrontendMavenModule(properties);
  }

  public JHipsterModule buildFrontendGradleModule(JHipsterModuleProperties properties) {
    return frontendJavaBuildTool.buildFrontendGradleModule(properties);
  }

  public JHipsterModule buildFrontendMavenCacheModule(JHipsterModuleProperties properties) {
    return frontendJavaBuildTool.buildFrontendMavenCacheModule(properties);
  }

  public JHipsterModule buildMergeCypressCoverageModule(JHipsterModuleProperties properties) {
    return frontendJavaBuildTool.buildMergeCypressCoverageModule(properties);
  }
}
