package com.seed4j.module.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.projectfolder.domain.ProjectFolder;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Map;

class RestSeed4JModuleProperties {

  private final String projectFolder;
  private final boolean commit;
  private final Map<String, Object> parameters;

  RestSeed4JModuleProperties(
    @JsonProperty("projectFolder") String projectFolder,
    @JsonProperty("commit") Boolean commit,
    @JsonProperty("parameters") Map<String, Object> parameters
  ) {
    this.projectFolder = projectFolder;
    this.commit = Boolean.TRUE.equals(commit);
    this.parameters = parameters;
  }

  public Seed4JModuleProperties toDomain(ProjectFolder projectFolder) {
    Assert.notNull("projectFolder", projectFolder);

    assertValidProjectFolder(projectFolder);

    return new Seed4JModuleProperties(getProjectFolder(), isCommit(), getParameters());
  }

  private void assertValidProjectFolder(ProjectFolder projectFolder) {
    if (projectFolder.isInvalid(this.projectFolder)) {
      throw new InvalidProjectFolderException(this.projectFolder);
    }
  }

  @Schema(description = "Path to the project folder", requiredMode = RequiredMode.REQUIRED)
  public String getProjectFolder() {
    return projectFolder;
  }

  @Schema(description = "True to commit each module application, false otherwise", requiredMode = RequiredMode.REQUIRED)
  public boolean isCommit() {
    return commit;
  }

  @Schema(description = "Parameters to apply on modules")
  public Map<String, Object> getParameters() {
    return parameters;
  }
}
