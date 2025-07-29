package com.seed4j.statistic.domain;

import com.seed4j.shared.collection.domain.JHipsterCollections;
import java.util.Map;

public record ModuleProperties(Map<String, Object> properties) {
  public ModuleProperties(Map<String, Object> properties) {
    this.properties = JHipsterCollections.immutable(properties);
  }

  public Map<String, Object> get() {
    return properties();
  }
}
