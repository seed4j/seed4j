package com.seed4j.module.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seed4j.module.domain.JHipsterModulesToApply;
import com.seed4j.module.domain.SeedModuleSlug;
import com.seed4j.shared.projectfolder.domain.ProjectFolder;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;

@Schema(name = "JHipsterModulesToApply", description = "Information to apply multiple modules")
class RestJHipsterModulesToApply {

  private final Collection<String> modules;
  private final RestJHipsterModuleProperties properties;

  RestJHipsterModulesToApply(
    @JsonProperty("modules") Collection<String> modules,
    @JsonProperty("properties") RestJHipsterModuleProperties properties
  ) {
    this.modules = modules;
    this.properties = properties;
  }

  public JHipsterModulesToApply toDomain(ProjectFolder projectFolder) {
    return new JHipsterModulesToApply(getModules().stream().map(SeedModuleSlug::new).toList(), properties.toDomain(projectFolder));
  }

  @NotEmpty
  @Schema(description = "Slugs of the modules to apply", requiredMode = RequiredMode.REQUIRED)
  public Collection<String> getModules() {
    return modules;
  }

  @NotNull
  @Schema(description = "Properties for the modules to apply")
  public RestJHipsterModuleProperties getProperties() {
    return properties;
  }
}
