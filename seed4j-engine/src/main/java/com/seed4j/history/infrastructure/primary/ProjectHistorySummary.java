package com.seed4j.history.infrastructure.primary;

import com.seed4j.shared.collection.domain.Seed4JCollections;
import java.util.Collection;
import java.util.Map;

public record ProjectHistorySummary(Collection<String> modules, Map<String, Object> properties) {
  public ProjectHistorySummary {
    modules = Seed4JCollections.immutable(modules);
    properties = Seed4JCollections.immutable(properties);
  }
}
