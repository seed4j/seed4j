package tech.jhipster.lite.module.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import tech.jhipster.lite.module.domain.JHipsterModuleSlug;
import tech.jhipster.lite.module.domain.JHipsterModulesToApply;
import tech.jhipster.lite.shared.projectfolder.domain.ProjectFolder;

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

  public JHipsterModulesToApply toDomain(ProjectFolder projectFolder, Collection<JHipsterModuleSlug> alreadyApplied) {
    var newModules = getModules().stream().map(JHipsterModuleSlug::new).toList();
    return new JHipsterModulesToApply(newModules, properties.toDomain(projectFolder, newModules, alreadyApplied));
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
