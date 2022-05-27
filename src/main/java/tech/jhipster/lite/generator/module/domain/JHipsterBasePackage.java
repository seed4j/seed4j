package tech.jhipster.lite.generator.module.domain;

import org.apache.commons.lang3.StringUtils;

public record JHipsterBasePackage(String basePackage) {
  public static final String DEFAULT_BASE_PACKAGE = "com.mycompany.myapp";

  public JHipsterBasePackage(String basePackage) {
    this.basePackage = buildBasePackage(basePackage);
  }

  private String buildBasePackage(String basePackage) {
    if (StringUtils.isBlank(basePackage)) {
      return DEFAULT_BASE_PACKAGE;
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
