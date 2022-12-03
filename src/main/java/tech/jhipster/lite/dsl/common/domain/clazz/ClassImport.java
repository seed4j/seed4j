package tech.jhipster.lite.dsl.common.domain.clazz;

import tech.jhipster.lite.error.domain.Assert;

public record ClassImport(String aImport, boolean isStatic) {
  public static ClassImport EMPTY = new ClassImport("", false);
  public ClassImport(String aImport, boolean isStatic) {
    Assert.field("aImport", aImport).noWhitespace();
    this.aImport = buildBaseImport(aImport);
    this.isStatic = isStatic;
  }
  private String buildBaseImport(String baseImport) {
    return baseImport.replace('/', '.');
  }
  public String get() {
    return aImport();
  }

  public boolean isWildcard() {
    return aImport.endsWith(".*");
  }
}
