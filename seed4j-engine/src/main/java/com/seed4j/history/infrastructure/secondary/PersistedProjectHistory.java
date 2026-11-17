package com.seed4j.history.infrastructure.secondary;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;

final class PersistedProjectHistory {

  private final Collection<PersistedProjectAction> actions;

  private PersistedProjectHistory(@JsonProperty("actions") Collection<PersistedProjectAction> actions) {
    this.actions = actions;
  }

  public Collection<PersistedProjectAction> getActions() {
    return actions;
  }
}
