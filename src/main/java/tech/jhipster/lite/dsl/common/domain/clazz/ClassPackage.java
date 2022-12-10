package tech.jhipster.lite.dsl.common.domain.clazz;

import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.error.domain.Assert;

public record ClassPackage(String packag) {
  public static ClassPackage EMPTY = new ClassPackage("");
  public ClassPackage(String packag) {
    Assert.field("packag", packag).noWhitespace();
    this.packag = buildBasePackage(packag);
  }
  private String buildBasePackage(String basePackage) {
    return basePackage.replace('/', '.');
  }
  public String get() {
    return packag();
  }

  public String path() {
    return packag().replace('.', '/');
  }

  public boolean isEmpty() {
    return packag().isEmpty();
  }
}
