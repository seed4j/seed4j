package com.seed4j.module.domain.javabuild;

import com.seed4j.module.domain.Seed4JProjectFilePath;

public enum JavaBuildTool {
  MAVEN("target"),
  GRADLE("build");

  private final Seed4JProjectFilePath buildDirectory;

  JavaBuildTool(String buildDirectory) {
    this.buildDirectory = new Seed4JProjectFilePath(buildDirectory);
  }

  public Seed4JProjectFilePath buildDirectory() {
    return buildDirectory;
  }
}
