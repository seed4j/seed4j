package com.seed4j.module.domain.javabuild;

import com.seed4j.module.domain.SeedProjectFilePath;

public enum JavaBuildTool {
  MAVEN("target"),
  GRADLE("build");

  private final SeedProjectFilePath buildDirectory;

  JavaBuildTool(String buildDirectory) {
    this.buildDirectory = new SeedProjectFilePath(buildDirectory);
  }

  public SeedProjectFilePath buildDirectory() {
    return buildDirectory;
  }
}
