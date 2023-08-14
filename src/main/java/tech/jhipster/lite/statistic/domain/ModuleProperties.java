package tech.jhipster.lite.statistic.domain;

import java.util.Map;
import tech.jhipster.lite.shared.collection.domain.JHipsterCollections;

public record ModuleProperties(Map<String, Object> properties) {
  public ModuleProperties(Map<String, Object> properties) {
    this.properties = JHipsterCollections.immutable(properties);
  }

  public Map<String, Object> get() {
    return properties();
  }
}
