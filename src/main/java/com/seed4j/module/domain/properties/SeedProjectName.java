package com.seed4j.module.domain.properties;

import org.apache.commons.lang3.StringUtils;

public record SeedProjectName(String projectName) {
  private static final String DEFAULT_NAME = "Seed4J Project";
  public static final SeedProjectName DEFAULT = new SeedProjectName(DEFAULT_NAME);

  public SeedProjectName(String projectName) {
    this.projectName = buildProjectName(projectName);
  }

  private String buildProjectName(String projectName) {
    if (StringUtils.isBlank(projectName)) {
      return DEFAULT_NAME;
    }

    return projectName;
  }

  public String get() {
    return projectName();
  }
}
