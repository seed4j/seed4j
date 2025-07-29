package com.seed4j.module.domain.properties;

import org.apache.commons.lang3.StringUtils;

public record JHipsterProjectName(String projectName) {
  private static final String DEFAULT_NAME = "JHipster Project";
  public static final JHipsterProjectName DEFAULT = new JHipsterProjectName(DEFAULT_NAME);

  public JHipsterProjectName(String projectName) {
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
