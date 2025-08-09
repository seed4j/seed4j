package com.seed4j.module.domain.properties;

import org.apache.commons.lang3.StringUtils;

public record SeedBasePackage(String basePackage) {
  private static final String DEFAULT_PACKAGE = "com.mycompany.myapp";
  public static final SeedBasePackage DEFAULT = new SeedBasePackage(DEFAULT_PACKAGE);

  public SeedBasePackage(String basePackage) {
    this.basePackage = buildBasePackage(basePackage);
  }

  private String buildBasePackage(String basePackage) {
    if (StringUtils.isBlank(basePackage)) {
      return DEFAULT_PACKAGE;
    }

    return basePackage.replace('/', '.');
  }

  public String get() {
    return basePackage();
  }

  public String path() {
    return basePackage().replace('.', '/');
  }

  @Override
  public String toString() {
    return basePackage;
  }
}
