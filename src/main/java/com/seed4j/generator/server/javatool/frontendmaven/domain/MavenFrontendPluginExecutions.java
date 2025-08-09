package com.seed4j.generator.server.javatool.frontendmaven.domain;

import static com.seed4j.module.domain.SeedModule.pluginExecution;
import static com.seed4j.module.domain.mavenplugin.MavenBuildPhase.GENERATE_RESOURCES;
import static com.seed4j.module.domain.mavenplugin.MavenBuildPhase.TEST;

import com.seed4j.module.domain.mavenplugin.MavenPluginExecution;
import com.seed4j.module.domain.mavenplugin.MavenPluginExecutionGoal;
import com.seed4j.module.domain.nodejs.NodePackageManager;

final class MavenFrontendPluginExecutions {

  private static final MavenPluginExecutionGoal NPM = new MavenPluginExecutionGoal("npm");
  private static final MavenPluginExecutionGoal COREPACK = new MavenPluginExecutionGoal("corepack");

  private MavenFrontendPluginExecutions() {}

  static MavenPluginExecution.MavenPluginExecutionOptionalBuilder installNode(NodePackageManager nodePackageManager) {
    return switch (nodePackageManager) {
      case NPM -> pluginExecution()
        .goals("install-node-and-npm")
        .id("install-node-and-npm")
        .configuration(
          """
          <nodeVersion>${node.version}</nodeVersion>
          <npmVersion>${npm.version}</npmVersion>
          """
        );
      case PNPM -> pluginExecution()
        .goals("install-node-and-corepack")
        .id("install-node-and-corepack")
        .configuration(
          """
          <nodeVersion>${node.version}</nodeVersion>
          """
        );
    };
  }

  static MavenPluginExecution.MavenPluginExecutionOptionalBuilder installPackages(NodePackageManager nodePackageManager) {
    return switch (nodePackageManager) {
      case NPM -> pluginExecution().goals("npm").id("npm install");
      case PNPM -> pluginExecution().goals(COREPACK).id("pnpm install").configuration("<arguments>pnpm install</arguments>");
    };
  }

  static MavenPluginExecution.MavenPluginExecutionOptionalBuilder buildFront(NodePackageManager nodePackageManager) {
    return switch (nodePackageManager) {
      case NPM -> pluginExecution()
        .goals(NPM)
        .id("build front")
        .phase(GENERATE_RESOURCES)
        .configuration(
          """
          <arguments>run build</arguments>
          <environmentVariables>
            <APP_VERSION>${project.version}</APP_VERSION>
          </environmentVariables>
          <npmInheritsProxyConfigFromMaven>false</npmInheritsProxyConfigFromMaven>
          """
        );
      case PNPM -> pluginExecution()
        .goals(COREPACK)
        .id("build front")
        .phase(GENERATE_RESOURCES)
        .configuration(
          """
          <arguments>pnpm build</arguments>
          <environmentVariables>
            <APP_VERSION>${project.version}</APP_VERSION>
          </environmentVariables>
          """
        );
    };
  }

  static MavenPluginExecution.MavenPluginExecutionOptionalBuilder testFront(NodePackageManager nodePackageManager) {
    return runNodeScriptDuringTestPhase("test:coverage", nodePackageManager, "front test");
  }

  static MavenPluginExecution.MavenPluginExecutionOptionalBuilder componentTest(NodePackageManager nodePackageManager) {
    return runNodeScriptDuringTestPhase("test:component:headless", nodePackageManager, "front component test");
  }

  static MavenPluginExecution.MavenPluginExecutionOptionalBuilder testCoverageCheck(NodePackageManager nodePackageManager) {
    return runNodeScriptDuringTestPhase("test:coverage:check", nodePackageManager, "front verify coverage");
  }

  private static MavenPluginExecution.MavenPluginExecutionOptionalBuilder runNodeScriptDuringTestPhase(
    String nodeScriptName,
    NodePackageManager nodePackageManager,
    String pluginExecutionId
  ) {
    return switch (nodePackageManager) {
      case NPM -> pluginExecution()
        .goals(NPM)
        .id(pluginExecutionId)
        .phase(TEST)
        .configuration(
          """
          <arguments>run %s</arguments>
          <npmInheritsProxyConfigFromMaven>false</npmInheritsProxyConfigFromMaven>
          """.formatted(nodeScriptName)
        );
      case PNPM -> pluginExecution()
        .goals(COREPACK)
        .id(pluginExecutionId)
        .phase(TEST)
        .configuration("<arguments>pnpm %s</arguments>".formatted(nodeScriptName));
    };
  }
}
