package tech.jhipster.lite.dsl.common.domain.clazz;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.jhipster.lite.error.domain.Assert;

public enum ClassType {
  CLASS,
  RECORD;

  private static final Map<String, ClassType> TYPES = buildScopes();

  private static Map<String, ClassType> buildScopes() {
    return Stream.of(values()).collect(Collectors.toUnmodifiableMap(scope -> scope.name().toLowerCase(), Function.identity()));
  }

  public static ClassType from(String type) {
    Assert.field("type", type).notBlank().noWhitespace();

    return TYPES.get(type.toLowerCase());
  }

  public String key() {
    return name().toLowerCase();
  }
}
