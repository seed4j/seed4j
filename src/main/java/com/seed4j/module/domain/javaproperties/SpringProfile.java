package com.seed4j.module.domain.javaproperties;

import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

public record SpringProfile(@Nullable String profile) {
  public static final SpringProfile DEFAULT = new SpringProfile(null);
  public static final SpringProfile LOCAL = new SpringProfile("local");
  public static final SpringProfile TEST = new SpringProfile("test");

  public @Nullable String get() {
    return profile();
  }

  public boolean isDefault() {
    return StringUtils.isBlank(profile);
  }
}
