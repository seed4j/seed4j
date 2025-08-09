package com.seed4j.module.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.projectfolder.domain.ProjectFolder;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Map;

class RestSeedModuleProperties {

  private final String projectFolder;
  private final boolean commit;
  private final Map<String, Object> parameters;

  RestSeedModuleProperties(
    @JsonProperty("projectFolder") String projectFolder,
    @JsonProperty("commit") boolean commit,
    @JsonProperty("parameters") Map<String, Object> parameters
  ) {
    this.projectFolder = projectFolder;
    this.commit = commit;
    this.parameters = parameters;
  }

  public SeedModuleProperties toDomain(ProjectFolder jHipsterProjectFolderFactory) {
    Assert.notNull("jHipsterProjectFolderFactory", jHipsterProjectFolderFactory);

    assertValidProjectFolder(jHipsterProjectFolderFactory);

    return new SeedModuleProperties(getProjectFolder(), isCommit(), getParameters());
  }

  private void assertValidProjectFolder(ProjectFolder jHipsterProjectFolderFactory) {
    if (jHipsterProjectFolderFactory.isInvalid(projectFolder)) {
      throw new InvalidProjectFolderException(projectFolder);
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
