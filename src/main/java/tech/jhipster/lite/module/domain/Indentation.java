package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.shared.error.domain.Assert;

public record Indentation(int spacesCount) {
  public static final Indentation DEFAULT = new Indentation(2);

  private static final String SPACE = " ";

  public Indentation {
    Assert.field("spacesCount", spacesCount).min(1);
  }

  public static Indentation from(Integer spacesCount) {
    if (invalidSpacesCount(spacesCount)) {
      return DEFAULT;
    }

    return new Indentation(spacesCount);
  }

  private static boolean invalidSpacesCount(Integer spacesCount) {
    return spacesCount == null || spacesCount.intValue() < 1;
  }

  public String times(int times) {
    Assert.field("times", times).min(1);

    return spaces().repeat(times);
  }

  public String spaces() {
    return SPACE.repeat(spacesCount());
  }
}
