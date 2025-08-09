package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.application.JHipsterModulesApplicationService;
import com.seed4j.module.domain.JHipsterModuleToApply;
import com.seed4j.module.domain.SeedModuleSlug;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.shared.projectfolder.domain.ProjectFolder;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Modules")
@RequestMapping("/api")
class ModulesResource {

  private final JHipsterModulesApplicationService modules;
  private final ProjectFolder projectFolder;

  private final RestJHipsterModules modulesList;
  private final RestJHipsterLandscape modulesLandscape;

  public ModulesResource(JHipsterModulesApplicationService modules, ProjectFolder projectFolder) {
    this.modules = modules;
    this.projectFolder = projectFolder;

    modulesList = RestJHipsterModules.from(modules.resources());
    modulesLandscape = RestJHipsterLandscape.from(modules.landscape());
  }

  @GetMapping("/modules")
  @Operation(summary = "List available modules")
  public ResponseEntity<RestJHipsterModules> listModules() {
    return ResponseEntity.ok(modulesList);
  }

  @GetMapping("modules-landscape")
  @Operation(summary = "Get a view of the current modules landscape")
  public ResponseEntity<RestJHipsterLandscape> modulesLandscape() {
    return ResponseEntity.ok(modulesLandscape);
  }

  @PostMapping("apply-patches")
  @Operation(summary = "Apply multiple modules patches")
  public void applyPatches(@RequestBody @Validated RestJHipsterModulesToApply modulesToApply) {
    modules.apply(modulesToApply.toDomain(projectFolder));
  }

  @Hidden
  @PostMapping("modules/{slug}/apply-patch")
  public void applyPatch(@RequestBody @Validated RestJHipsterModuleProperties restProperties, @PathVariable("slug") String slug) {
    SeedModuleProperties properties = restProperties.toDomain(projectFolder);
    modules.apply(new JHipsterModuleToApply(new SeedModuleSlug(slug), properties));
  }

  @Hidden
  @GetMapping("modules/{slug}")
  public RestJHipsterModulePropertiesDefinition propertiesDefinition(@PathVariable("slug") String slug) {
    JHipsterModuleResource module = modules.resources().get(new SeedModuleSlug(slug));
    return RestJHipsterModulePropertiesDefinition.from(module.propertiesDefinition());
  }

  @Operation(summary = "Get presets configuration")
  @GetMapping(path = "/presets", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<RestPresets> getPresets() {
    return ResponseEntity.ok(RestPresets.from(modules.getPresets()));
  }
}
