package tech.jhipster.lite.dsl.common.domain;

import tech.jhipster.lite.error.domain.Assert;

public class DslProperties {

  private final String projectFolder;
  private final boolean commitModule;

  public DslProperties(String projectFolder, boolean commitModule) {
    Assert.notNull("projectFolder", projectFolder);
    this.projectFolder = projectFolder;
    this.commitModule = commitModule;
  }

  public String projectFolder() {
    return projectFolder;
  }
}
