package tech.jhipster.lite.dsl.parser.domain.config;

import org.apache.commons.lang3.StringUtils;

public record ConfigPackageInfrastructurePrimaryName(String pkgInfrastructurePrimaryName) {
  private static final String DEFAULT_PACKAGE = "primary";
  public static final ConfigPackageInfrastructurePrimaryName DEFAULT = new ConfigPackageInfrastructurePrimaryName(DEFAULT_PACKAGE);

  public ConfigPackageInfrastructurePrimaryName(String pkgInfrastructurePrimaryName) {
    this.pkgInfrastructurePrimaryName = buildBasePackage(pkgInfrastructurePrimaryName);
  }

  private String buildBasePackage(String basePackage) {
    if (StringUtils.isBlank(basePackage)) {
      return DEFAULT_PACKAGE;
    }

    return basePackage.replace('/', '.');
  }

  public String get() {
    return pkgInfrastructurePrimaryName();
  }

  public String path() {
    return pkgInfrastructurePrimaryName().replace('.', '/');
  }
}
