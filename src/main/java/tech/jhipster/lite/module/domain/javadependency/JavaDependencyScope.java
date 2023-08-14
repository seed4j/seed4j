package tech.jhipster.lite.module.domain.javadependency;

import tech.jhipster.lite.shared.error.domain.Assert;

public enum JavaDependencyScope {
  COMPILE(6),
  IMPORT(5),
  PROVIDED(4),
  SYSTEM(3),
  RUNTIME(2),
  TEST(1);

  private final int priority;

  JavaDependencyScope(int priority) {
    this.priority = priority;
  }

  static JavaDependencyScope from(JavaDependencyScope scope) {
    if (scope == null) {
      return COMPILE;
    }

    return scope;
  }

  JavaDependencyScope merge(JavaDependencyScope other) {
    Assert.notNull("other", other);

    if (other.priority > priority) {
      return other;
    }

    return this;
  }
}
