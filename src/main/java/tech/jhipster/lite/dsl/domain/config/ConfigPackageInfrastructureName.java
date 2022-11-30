package tech.jhipster.lite.dsl.domain.config;

import org.apache.commons.lang3.StringUtils;

public record ConfigPackageInfrastructureName(String pkgInfrastructureName) {
  private static final String DEFAULT_PACKAGE = "infrastructure";
  public static final ConfigPackageInfrastructureName DEFAULT = new ConfigPackageInfrastructureName(DEFAULT_PACKAGE);

  public ConfigPackageInfrastructureName(String pkgInfrastructureName) {
    this.pkgInfrastructureName = buildBasePackage(pkgInfrastructureName);
  }

  private String buildBasePackage(String basePackage) {
    if (StringUtils.isBlank(basePackage)) {
      return DEFAULT_PACKAGE;
    }

    return basePackage.replace('/', '.');
  }

  public String get() {
    return pkgInfrastructureName();
  }

  public String path() {
    return pkgInfrastructureName().replace('.', '/');
  }
}
