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
public class JHipsterModulesApplicationService {

  private final JHipsterModuleEvents events;
  private final JHipsterModulesRepository modules;
  private final JHipsterModulesApplyer applyer;
  private final JHipsterPresetRepository preset;

  public JHipsterModulesApplicationService(
    JHipsterModuleEvents events,
    JHipsterModulesRepository modules,
    JavaDependenciesVersionsRepository currentVersions,
    ProjectJavaDependenciesRepository projectDependencies,
    ProjectJavaBuildToolRepository javaBuildTools,
    GitRepository git,
    GeneratedProjectRepository generatedProject,
    JHipsterPresetRepository preset
  ) {
    this.events = events;
    this.modules = modules;
    this.preset = preset;

    applyer = new JHipsterModulesApplyer(modules, currentVersions, projectDependencies, javaBuildTools, git, generatedProject);
  }

  public void apply(JHipsterModulesToApply modulesToApply) {
    Collection<JHipsterModuleApplied> modulesApplied = applyer.apply(modulesToApply);

    modulesApplied.forEach(events::dispatch);
  }

  public void apply(JHipsterModuleToApply moduleToApply) {
    JHipsterModuleApplied moduleApplied = applyer.apply(moduleToApply);

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
