package com.seed4j.module.domain.properties;

import org.apache.commons.lang3.StringUtils;

public record Seed4JProjectName(String projectName) {
  private static final String DEFAULT_NAME = "Seed4J Project";
  public static final Seed4JProjectName DEFAULT = new Seed4JProjectName(DEFAULT_NAME);

  public Seed4JProjectName(String projectName) {
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
