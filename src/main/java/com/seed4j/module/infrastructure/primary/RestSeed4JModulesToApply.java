package com.seed4j.module.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seed4j.module.domain.Seed4JModuleSlug;
import com.seed4j.module.domain.Seed4JModulesToApply;
import com.seed4j.shared.projectfolder.domain.ProjectFolder;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;

@Schema(name = "Seed4JModulesToApply", description = "Information to apply multiple modules")
class RestSeed4JModulesToApply {

  private final Collection<String> modules;
  private final RestSeed4JModuleProperties properties;

  RestSeed4JModulesToApply(
    @JsonProperty("modules") Collection<String> modules,
    @JsonProperty("properties") RestSeed4JModuleProperties properties
  ) {
    this.modules = modules;
    this.properties = properties;
  }

  public Seed4JModulesToApply toDomain(ProjectFolder projectFolder) {
    return new Seed4JModulesToApply(getModules().stream().map(Seed4JModuleSlug::new).toList(), properties.toDomain(projectFolder));
  }

  @NotEmpty
  @Schema(description = "Slugs of the modules to apply", requiredMode = RequiredMode.REQUIRED)
  public Collection<String> getModules() {
    return modules;
  }

  @NotNull
  @Schema(description = "Properties for the modules to apply")
  public RestSeed4JModuleProperties getProperties() {
    return properties;
  }
}
