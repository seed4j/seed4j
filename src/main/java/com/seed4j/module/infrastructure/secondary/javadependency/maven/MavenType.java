package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import com.seed4j.module.domain.javadependency.JavaDependencyType;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jspecify.annotations.Nullable;

enum MavenType {
  POM("pom"),
  TEST_JAR("test-jar");

  private static final Map<String, MavenType> TYPES = buildTypes();

  private final String key;

  MavenType(String key) {
    this.key = key;
  }

  private static Map<String, MavenType> buildTypes() {
    return Stream.of(values()).collect(Collectors.toMap(MavenType::key, Function.identity()));
  }

  String key() {
    return key;
  }

  static Optional<MavenType> from(@Nullable String type) {
    if (type == null) {
      return Optional.empty();
    }

    return Optional.ofNullable(TYPES.get(type));
  }

  public static MavenType from(JavaDependencyType javaDependencyType) {
    return switch (javaDependencyType) {
      case POM -> POM;
      case TEST_JAR -> TEST_JAR;
    };
  }

  public JavaDependencyType toJavaDependencyType() {
    return switch (this) {
      case POM -> JavaDependencyType.POM;
      case TEST_JAR -> JavaDependencyType.TEST_JAR;
    };
  }
}
