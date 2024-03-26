package tech.jhipster.lite.project.domain.history;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import tech.jhipster.lite.project.domain.ProjectPath;
import tech.jhipster.lite.shared.collection.domain.JHipsterCollections;
import tech.jhipster.lite.shared.error.domain.Assert;

public class ProjectHistory {

  private final ProjectPath path;
  private Collection<ProjectAction> actions;

  public ProjectHistory(ProjectPath path, Collection<ProjectAction> actions) {
    Assert.notNull("path", path);

    this.path = path;
    this.actions = JHipsterCollections.immutable(actions);
  }

  public static ProjectHistory empty(ProjectPath path) {
    return new ProjectHistory(path, List.of());
  }

  public void append(ProjectAction action) {
    Assert.notNull("action", action);

    actions = Stream.concat(Stream.of(action), actions.stream()).sorted(Comparator.comparing(ProjectAction::date)).toList();
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

  public Collection<String> getAppliedModules() {
    return actions.stream().map(ProjectAction::module).map(ModuleSlug::slug).toList();
  }
}
