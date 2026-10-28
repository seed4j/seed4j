package com.seed4j.module.domain.gitignore;

import com.seed4j.shared.error.domain.Assert;

public sealed interface GitIgnoreEntry {
  String get();

  record GitIgnorePattern(String value) implements GitIgnoreEntry {
    public GitIgnorePattern {
      Assert.notBlank("value", value);
    }

    @Override
    public String get() {
      return value;
    }

    @Override
    public String toString() {
      return value;
    }
  }

  record GitIgnoreComment(String value) implements GitIgnoreEntry {
    private static final String COMMENT_PREFIX = "#";

    public GitIgnoreComment(String value) {
      Assert.notBlank("value", value);
      this.value = value.startsWith(COMMENT_PREFIX) ? value : COMMENT_PREFIX + " " + value;
    }

    @Override
    public String get() {
      return value;
    }

    @Override
    public String toString() {
      return value;
    }
  }
}
