package com.seed4j.module.application;

import com.seed4j.module.domain.*;
import com.seed4j.module.domain.git.GitRepository;
import com.seed4j.module.domain.javabuild.ProjectJavaBuildToolRepository;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersionsRepository;
import com.seed4j.module.domain.javadependency.ProjectJavaDependenciesRepository;
import com.seed4j.module.domain.landscape.SeedLandscape;
import com.seed4j.module.domain.preset.Presets;
import com.seed4j.module.domain.resource.SeedModulesResources;
import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public class SeedModulesApplicationService {

  private final SeedModuleEvents events;
  private final SeedModulesRepository modules;
  private final SeedModulesApplyer applyer;
  private final SeedPresetRepository preset;

  public SeedModulesApplicationService(
    SeedModuleEvents events,
    SeedModulesRepository modules,
    JavaDependenciesVersionsRepository currentVersions,
    ProjectJavaDependenciesRepository projectDependencies,
    ProjectJavaBuildToolRepository javaBuildTools,
    GitRepository git,
    GeneratedProjectRepository generatedProject,
    SeedPresetRepository preset
  ) {
    this.events = events;
    this.modules = modules;
    this.preset = preset;

    applyer = new SeedModulesApplyer(modules, currentVersions, projectDependencies, javaBuildTools, git, generatedProject);
  }

  public void apply(SeedModulesToApply modulesToApply) {
    Collection<SeedModuleApplied> modulesApplied = applyer.apply(modulesToApply);

    modulesApplied.forEach(events::dispatch);
  }

  public void apply(SeedModuleToApply moduleToApply) {
    SeedModuleApplied moduleApplied = applyer.apply(moduleToApply);

    events.dispatch(moduleApplied);
  }

  public SeedModulesResources resources() {
    return modules.resources();
  }

  public SeedLandscape landscape() {
    return modules.landscape();
  }

  public Presets getPresets() {
    return preset.getPresets();
  }
}
