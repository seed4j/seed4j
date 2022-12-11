package tech.jhipster.lite.dsl.common.domain;

import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

public class DslProperties {

  private final JHipsterProjectFolder projectFolder;
  private final boolean commitModule;

  public DslProperties(String projectFolder, boolean commitModule) {
    this.projectFolder = new JHipsterProjectFolder(projectFolder);
    this.commitModule = commitModule;
  }

  public JHipsterProjectFolder projectFolder() {
    return projectFolder;
  }

  public boolean commitNeeded() {
    return commitModule;
  }
}
