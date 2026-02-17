package com.seed4j.module.domain;

import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.nullness.domain.Contract;
import org.jspecify.annotations.Nullable;

public record Indentation(int spacesCount) {
  public static final Indentation DEFAULT = new Indentation(2);

  private static final String SPACE = " ";

  public Indentation {
    Assert.field("spacesCount", spacesCount).min(1);
  }

  public static Indentation from(@Nullable Integer spacesCount) {
    if (invalidSpacesCount(spacesCount)) {
      return DEFAULT;
    }

    return new Indentation(spacesCount);
  }

  @Contract("null -> true")
  private static boolean invalidSpacesCount(@Nullable Integer spacesCount) {
    return spacesCount == null || spacesCount < 1;
  }

  public String times(int times) {
    Assert.field("times", times).min(1);

    return spaces().repeat(times);
  }

  public String spaces() {
    return SPACE.repeat(spacesCount());
  }
}
