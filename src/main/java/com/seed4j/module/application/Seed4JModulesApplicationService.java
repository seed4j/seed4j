package com.seed4j.module.application;

import com.seed4j.module.domain.*;
import com.seed4j.module.domain.git.GitRepository;
import com.seed4j.module.domain.javabuild.ProjectJavaBuildToolRepository;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersionsRepository;
import com.seed4j.module.domain.javadependency.ProjectJavaDependenciesRepository;
import com.seed4j.module.domain.landscape.Seed4JLandscape;
import com.seed4j.module.domain.preset.Presets;
import com.seed4j.module.domain.resource.Seed4JModulesResources;
import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public class Seed4JModulesApplicationService {

  private final Seed4JModuleEvents events;
  private final Seed4JModulesRepository modules;
  private final Seed4JModulesApplyer applyer;
  private final Seed4JPresetRepository preset;

  public Seed4JModulesApplicationService(
    Seed4JModuleEvents events,
    Seed4JModulesRepository modules,
    JavaDependenciesVersionsRepository currentVersions,
    ProjectJavaDependenciesRepository projectDependencies,
    ProjectJavaBuildToolRepository javaBuildTools,
    GitRepository git,
    GeneratedProjectRepository generatedProject,
    Seed4JPresetRepository preset
  ) {
    this.events = events;
    this.modules = modules;
    this.preset = preset;

    applyer = new Seed4JModulesApplyer(modules, currentVersions, projectDependencies, javaBuildTools, git, generatedProject);
  }

  public void apply(Seed4JModulesToApply modulesToApply) {
    Collection<Seed4JModuleApplied> modulesApplied = applyer.apply(modulesToApply);

    modulesApplied.forEach(events::dispatch);
  }

  public void apply(Seed4JModuleToApply moduleToApply) {
    Seed4JModuleApplied moduleApplied = applyer.apply(moduleToApply);

    events.dispatch(moduleApplied);
  }

  public Seed4JModulesResources resources() {
    return modules.resources();
  }

  public Seed4JLandscape landscape() {
    return modules.landscape();
  }

  public Presets getPresets() {
    return preset.getPresets();
  }
}
