package tech.jhipster.lite.dsl.domain.clazz;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.jhipster.lite.error.domain.Assert;

public enum DslClassType {
  CLASS,
  RECORD;

  private static final Map<String, DslClassType> TYPES = buildScopes();

  private static Map<String, DslClassType> buildScopes() {
    return Stream.of(values()).collect(Collectors.toUnmodifiableMap(scope -> scope.name().toLowerCase(), Function.identity()));
  }

  public static DslClassType from(String type) {
    Assert.field("type", type).notBlank().noWhitespace();

    return TYPES.get(type.toLowerCase());
  }

  public String key() {
    return name().toLowerCase();
  }
}
