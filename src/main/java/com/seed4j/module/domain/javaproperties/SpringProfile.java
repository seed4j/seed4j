package com.seed4j.module.domain.javaproperties;

import org.apache.commons.lang3.StringUtils;

public record SpringProfile(String profile) {
  public static final SpringProfile DEFAULT = new SpringProfile(null);
  public static final SpringProfile LOCAL = new SpringProfile("local");
  public static final SpringProfile TEST = new SpringProfile("test");

  public String get() {
    return profile();
  }

  public boolean isDefault() {
    return StringUtils.isBlank(profile);
  }
}
