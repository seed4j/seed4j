package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

enum MavenScope {
  COMPILE,
  IMPORT,
  PROVIDED,
  SYSTEM,
  RUNTIME,
  TEST;

  private static final Map<String, MavenScope> SCOPES = buildScopes();

  private static Map<String, MavenScope> buildScopes() {
    return Stream.of(values()).collect(Collectors.toUnmodifiableMap(MavenScope::key, Function.identity()));
  }

  static MavenScope from(String scope) {
    if (scope == null) {
      return null;
    }

    return SCOPES.get(scope.toLowerCase(Locale.ROOT));
  }

  String key() {
    return name().toLowerCase(Locale.ROOT);
  }
}
