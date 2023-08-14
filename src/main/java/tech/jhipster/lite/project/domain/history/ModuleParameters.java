package tech.jhipster.lite.project.domain.history;

import java.util.HashMap;
import java.util.Map;
import tech.jhipster.lite.shared.collection.domain.JHipsterCollections;
import tech.jhipster.lite.shared.error.domain.Assert;

public record ModuleParameters(Map<String, Object> parameters) {
  public static final ModuleParameters EMPTY = new ModuleParameters(Map.of());

  public ModuleParameters(Map<String, Object> parameters) {
    this.parameters = JHipsterCollections.immutable(parameters);
  }

  public Map<String, Object> get() {
    return parameters();
  }

  public ModuleParameters merge(ModuleParameters other) {
    Assert.notNull("other", other);

    Map<String, Object> mergedParameters = new HashMap<>();
    mergedParameters.putAll(parameters);
    mergedParameters.putAll(other.parameters);

    return new ModuleParameters(mergedParameters);
  }
}
