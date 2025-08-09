package com.seed4j.module.domain.resource;

import com.seed4j.shared.error.domain.Assert;
import java.util.regex.Pattern;

public record SeedModuleTag(String tag) {
  private static final Pattern TAG_FORMAT = Pattern.compile("^[a-z0-9-]+$");

  public SeedModuleTag {
    Assert.field("tag", tag)
      .notNull()
      .maxLength(15)
      .matchesPatternOrThrow(TAG_FORMAT, () -> new InvalidSeedModuleTagException(tag));
  }

  public String get() {
    return tag();
  }

  @Override
  public String toString() {
    return tag();
  }
}
