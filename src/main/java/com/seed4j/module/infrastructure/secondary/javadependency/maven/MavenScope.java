package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jspecify.annotations.Nullable;

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

  static Optional<MavenScope> from(@Nullable String scope) {
    return Optional.ofNullable(scope)
      .map(value -> value.toLowerCase(Locale.ROOT))
      .map(SCOPES::get);
  }

  String key() {
    return name().toLowerCase(Locale.ROOT);
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Pattern matching mapper doesn't need full coverage")
  public static MavenScope from(JavaDependencyScope scope) {
    return switch (scope) {
      case COMPILE -> COMPILE;
      case IMPORT -> IMPORT;
      case PROVIDED -> PROVIDED;
      case SYSTEM -> SYSTEM;
      case RUNTIME -> RUNTIME;
      case TEST -> TEST;
    };
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Pattern matching mapper doesn't need full coverage")
  public JavaDependencyScope toJavaDependencyScope() {
    return switch (this) {
      case COMPILE -> JavaDependencyScope.COMPILE;
      case IMPORT -> JavaDependencyScope.IMPORT;
      case PROVIDED -> JavaDependencyScope.PROVIDED;
      case SYSTEM -> JavaDependencyScope.SYSTEM;
      case RUNTIME -> JavaDependencyScope.RUNTIME;
      case TEST -> JavaDependencyScope.TEST;
    };
  }
}
