package tech.jhipster.lite.dsl.domain.clazz;

import tech.jhipster.lite.error.domain.Assert;

public record DslClassPackage(String packag) {
  public static DslClassPackage EMPTY = new DslClassPackage("");
  public DslClassPackage {
    Assert.field("packag", packag).noWhitespace();
  }

  public String get() {
    return packag();
  }

  public String path() {
    return packag().replace('.', '/');
  }
}
