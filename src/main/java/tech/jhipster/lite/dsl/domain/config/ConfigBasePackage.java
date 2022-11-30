package tech.jhipster.lite.dsl.domain.config;

import org.apache.commons.lang3.StringUtils;

public record ConfigBasePackage(String basePackage) {
  private static final String DEFAULT_PACKAGE = "com.mycompany.myapp";
  public static final ConfigBasePackage DEFAULT = new ConfigBasePackage(DEFAULT_PACKAGE);

  public ConfigBasePackage(String basePackage) {
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
}
