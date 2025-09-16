package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.application.Seed4JModulesApplicationService;
import com.seed4j.module.domain.Seed4JModuleSlug;
import com.seed4j.module.domain.Seed4JModuleToApply;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
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

  private final Seed4JModulesApplicationService modules;
  private final ProjectFolder projectFolder;

  private final RestSeed4JModules modulesList;
  private final RestSeed4JLandscape modulesLandscape;

  public ModulesResource(Seed4JModulesApplicationService modules, ProjectFolder projectFolder) {
    this.modules = modules;
    this.projectFolder = projectFolder;

    modulesList = RestSeed4JModules.from(modules.resources());
    modulesLandscape = RestSeed4JLandscape.from(modules.landscape());
  }

  @GetMapping("/modules")
  @Operation(summary = "List available modules")
  public ResponseEntity<RestSeed4JModules> listModules() {
    return ResponseEntity.ok(modulesList);
  }

  @GetMapping("modules-landscape")
  @Operation(summary = "Get a view of the current modules landscape")
  public ResponseEntity<RestSeed4JLandscape> modulesLandscape() {
    return ResponseEntity.ok(modulesLandscape);
  }

  @PostMapping("apply-patches")
  @Operation(summary = "Apply multiple modules patches")
  public void applyPatches(@RequestBody @Validated RestSeed4JModulesToApply modulesToApply) {
    modules.apply(modulesToApply.toDomain(projectFolder));
  }

  @Hidden
  @PostMapping("modules/{slug}/apply-patch")
  public void applyPatch(@RequestBody @Validated RestSeed4JModuleProperties restProperties, @PathVariable("slug") String slug) {
    Seed4JModuleProperties properties = restProperties.toDomain(projectFolder);
    modules.apply(new Seed4JModuleToApply(new Seed4JModuleSlug(slug), properties));
  }

  @Hidden
  @GetMapping("modules/{slug}")
  public RestSeed4JModulePropertiesDefinition propertiesDefinition(@PathVariable("slug") String slug) {
    Seed4JModuleResource module = modules.resources().get(new Seed4JModuleSlug(slug));
    return RestSeed4JModulePropertiesDefinition.from(module.propertiesDefinition());
  }

  @Operation(summary = "Get presets configuration")
  @GetMapping(path = "/presets", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<RestPresets> getPresets() {
    return ResponseEntity.ok(RestPresets.from(modules.getPresets()));
  }
}
