package com.seed4j.project.domain.history;

import com.seed4j.project.domain.ProjectPath;
import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;

public class ProjectHistory {

  private final ProjectPath path;
  private final Collection<ProjectAction> actions;

  public ProjectHistory(ProjectPath path, Collection<ProjectAction> actions) {
    Assert.notNull("path", path);

    this.path = path;
    this.actions = SeedCollections.immutable(actions);
  }

  public ProjectPath path() {
    return path;
  }

  public Collection<ProjectAction> actions() {
    return actions;
  }

  public ModuleParameters latestProperties() {
    return actions.stream().map(ProjectAction::parameters).reduce(ModuleParameters.EMPTY, ModuleParameters::merge);
  }
}
