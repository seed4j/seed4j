package tech.jhipster.lite.dsl.common.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import tech.jhipster.lite.dsl.common.domain.DslProperties;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.projectfolder.domain.ProjectFolder;

class RestJHipsterDslProperties {

  private final String projectFolder;
  private final boolean commit;

  RestJHipsterDslProperties(@JsonProperty("projectFolder") String projectFolder, @JsonProperty("commit") boolean commit) {
    this.projectFolder = projectFolder;
    this.commit = commit;
  }

  public DslProperties toDomain(ProjectFolder jHipsterProjectFolderFactory) {
    Assert.notNull("jHipsterProjectFolderFactory", jHipsterProjectFolderFactory);

    assertValidProjectFolder(jHipsterProjectFolderFactory);

    return new DslProperties(getProjectFolder(), isCommit());
  }

  private void assertValidProjectFolder(ProjectFolder jHipsterProjectFolderFactory) {
    if (jHipsterProjectFolderFactory.isInvalid(projectFolder)) {
      throw new InvalidProjectFolderException();
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
}
