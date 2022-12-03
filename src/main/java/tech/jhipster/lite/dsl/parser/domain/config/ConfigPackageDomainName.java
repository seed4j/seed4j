package tech.jhipster.lite.dsl.parser.domain.config;

import org.apache.commons.lang3.StringUtils;

public record ConfigPackageDomainName(String pkgDomainName) {
  private static final String DEFAULT_PACKAGE = "domain";
  public static final ConfigPackageDomainName DEFAULT = new ConfigPackageDomainName(DEFAULT_PACKAGE);

  public ConfigPackageDomainName(String pkgDomainName) {
    this.pkgDomainName = buildBasePackage(pkgDomainName);
  }

  private String buildBasePackage(String basePackage) {
    if (StringUtils.isBlank(basePackage)) {
      return DEFAULT_PACKAGE;
    }

    return basePackage.replace('/', '.');
  }

  public String get() {
    return pkgDomainName();
  }

  public String path() {
    return pkgDomainName().replace('.', '/');
  }
}
