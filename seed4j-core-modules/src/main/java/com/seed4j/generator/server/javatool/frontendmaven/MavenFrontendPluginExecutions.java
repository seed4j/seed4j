package com.seed4j.generator.server.javatool.frontendmaven;

import static com.seed4j.module.domain.Seed4JModule.pluginExecution;
import static com.seed4j.module.domain.mavenplugin.MavenBuildPhase.GENERATE_RESOURCES;
import static com.seed4j.module.domain.mavenplugin.MavenBuildPhase.TEST;

import com.seed4j.module.domain.mavenplugin.MavenPluginExecution;
import com.seed4j.module.domain.mavenplugin.MavenPluginExecutionGoal;
import com.seed4j.module.domain.nodejs.NodePackageManager;

final class MavenFrontendPluginExecutions {

  private static final MavenPluginExecutionGoal NPM = new MavenPluginExecutionGoal("npm");
  private static final MavenPluginExecutionGoal PNPM = new MavenPluginExecutionGoal("pnpm");

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
        .goals("install-node-and-pnpm")
        .id("install-node-and-pnpm")
        .configuration(
          """
          <nodeVersion>${node.version}</nodeVersion>
          <pnpmVersion>${pnpm.version}</pnpmVersion>
          """
        );
    };
  }

  static MavenPluginExecution.MavenPluginExecutionOptionalBuilder installPackages(NodePackageManager nodePackageManager) {
    return switch (nodePackageManager) {
      case NPM -> pluginExecution().goals(NPM).id("npm install");
      case PNPM -> pluginExecution().goals(PNPM).id("pnpm install");
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
        .goals(PNPM)
        .id("build front")
        .phase(GENERATE_RESOURCES)
        .configuration(
          """
          <arguments>run build</arguments>
          <environmentVariables>
            <APP_VERSION>${project.version}</APP_VERSION>
          </environmentVariables>
          <pnpmInheritsProxyConfigFromMaven>false</pnpmInheritsProxyConfigFromMaven>
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
        .goals(PNPM)
        .id(pluginExecutionId)
        .phase(TEST)
        .configuration(
          """
          <arguments>run %s</arguments>
          <pnpmInheritsProxyConfigFromMaven>false</pnpmInheritsProxyConfigFromMaven>
          """.formatted(nodeScriptName)
        );
    };
  }
}
