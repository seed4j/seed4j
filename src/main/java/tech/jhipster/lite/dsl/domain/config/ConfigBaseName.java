package tech.jhipster.lite.dsl.domain.config;

import org.apache.commons.lang3.StringUtils;

public record ConfigBaseName(String baseName) {
  private static final String DEFAULT_BASE_NAME = "jhlite";
  public static final ConfigBaseName DEFAULT = new ConfigBaseName(DEFAULT_BASE_NAME);

  public ConfigBaseName(String baseName) {
    this.baseName = buildBaseName(baseName);
  }

  private String buildBaseName(String baseName) {
    if (StringUtils.isBlank(baseName)) {
      return DEFAULT_BASE_NAME;
    }
    return baseName;
  }

  public String get() {
    return baseName();
  }
}
