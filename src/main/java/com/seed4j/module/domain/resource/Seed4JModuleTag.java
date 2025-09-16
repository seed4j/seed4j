package com.seed4j.module.domain.resource;

import com.seed4j.shared.error.domain.Assert;
import java.util.regex.Pattern;

public record Seed4JModuleTag(String tag) {
  private static final Pattern TAG_FORMAT = Pattern.compile("^[a-z0-9-]+$");

  public Seed4JModuleTag {
    Assert.field("tag", tag)
      .notNull()
      .maxLength(15)
      .matchesPatternOrThrow(TAG_FORMAT, () -> new InvalidSeed4JModuleTagException(tag));
  }

  public String get() {
    return tag();
  }

  @Override
  public String toString() {
    return tag();
  }
}
