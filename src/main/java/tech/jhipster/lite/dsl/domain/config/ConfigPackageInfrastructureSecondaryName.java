package tech.jhipster.lite.dsl.domain.config;

import org.apache.commons.lang3.StringUtils;

public record ConfigPackageInfrastructureSecondaryName(String pkgInfrastructureSecondaryName) {
  private static final String DEFAULT_PACKAGE = "secondary";
  public static final ConfigPackageInfrastructureSecondaryName DEFAULT = new ConfigPackageInfrastructureSecondaryName(DEFAULT_PACKAGE);

  public ConfigPackageInfrastructureSecondaryName(String pkgInfrastructureSecondaryName) {
    this.pkgInfrastructureSecondaryName = buildBasePackage(pkgInfrastructureSecondaryName);
  }

  private String buildBasePackage(String basePackage) {
    if (StringUtils.isBlank(basePackage)) {
      return DEFAULT_PACKAGE;
    }

    return basePackage.replace('/', '.');
  }

  public String get() {
    return pkgInfrastructureSecondaryName();
  }

  public String path() {
    return pkgInfrastructureSecondaryName().replace('.', '/');
  }
}
