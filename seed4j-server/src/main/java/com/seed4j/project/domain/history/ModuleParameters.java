package com.seed4j.project.domain.history;

import com.seed4j.shared.collection.domain.Seed4JCollections;
import java.util.Map;

public record ModuleParameters(Map<String, Object> parameters) {
  public ModuleParameters {
    parameters = Seed4JCollections.immutable(parameters);
  }

  public Map<String, Object> get() {
    return parameters();
  }
}
