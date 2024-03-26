package tech.jhipster.lite.module.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Collection;
import java.util.Map;
import tech.jhipster.lite.module.domain.JHipsterModuleSlug;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.projectfolder.domain.ProjectFolder;

class RestJHipsterModuleProperties {

  private final String projectFolder;
  private final boolean commit;
  private final Map<String, Object> parameters;

  RestJHipsterModuleProperties(
    @JsonProperty("projectFolder") String projectFolder,
    @JsonProperty("commit") boolean commit,
    @JsonProperty("parameters") Map<String, Object> parameters
  ) {
    this.projectFolder = projectFolder;
    this.commit = commit;
    this.parameters = parameters;
  }

  public JHipsterModuleProperties toDomain(
    ProjectFolder jHipsterProjectFolderFactory,
    Collection<JHipsterModuleSlug> newModules,
    Collection<JHipsterModuleSlug> alreadyApplied
  ) {
    Assert.notNull("jHipsterProjectFolderFactory", jHipsterProjectFolderFactory);
    Assert.notNull("newModules", newModules);
    Assert.notNull("alreadyApplied", alreadyApplied);

    assertValidProjectFolder(jHipsterProjectFolderFactory);

    return new JHipsterModuleProperties(getProjectFolder(), isCommit(), getParameters(), newModules, alreadyApplied);
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

  @Schema(description = "Parameters to apply on modules")
  public Map<String, Object> getParameters() {
    return parameters;
  }
}
