package com.seed4j.module.domain.javabuild;

import com.seed4j.module.domain.JHipsterProjectFilePath;

public enum JavaBuildTool {
  MAVEN("target"),
  GRADLE("build");

  private final JHipsterProjectFilePath buildDirectory;

  JavaBuildTool(String buildDirectory) {
    this.buildDirectory = new JHipsterProjectFilePath(buildDirectory);
  }

  public JHipsterProjectFilePath buildDirectory() {
    return buildDirectory;
  }
}
