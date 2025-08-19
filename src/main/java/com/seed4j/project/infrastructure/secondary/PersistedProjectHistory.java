package com.seed4j.project.infrastructure.secondary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seed4j.project.domain.ProjectPath;
import com.seed4j.project.domain.history.ProjectHistory;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

final class PersistedProjectHistory {

  static final PersistedProjectHistory EMPTY = new PersistedProjectHistory(List.of());
  private Collection<PersistedProjectAction> actions;

  private PersistedProjectHistory(@JsonProperty("actions") Collection<PersistedProjectAction> actions) {
    this.actions = actions;
  }

  public ProjectHistory toDomain(ProjectPath path) {
    return new ProjectHistory(path, getActions().stream().map(PersistedProjectAction::toDomain).toList());
  }

  public Collection<PersistedProjectAction> getActions() {
    return actions;
  }

  public void addAll(final Collection<PersistedProjectAction> projectActions) {
    actions = Stream.concat(actions.stream(), projectActions.stream()).toList();
  }
}
