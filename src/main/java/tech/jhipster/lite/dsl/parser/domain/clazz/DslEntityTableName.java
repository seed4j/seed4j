package tech.jhipster.lite.dsl.parser.domain.clazz;

import tech.jhipster.lite.error.domain.Assert;

public record DslEntityTableName(String tableName) {
  public DslEntityTableName {
    Assert.field("tableName", tableName).noWhitespace().maxLength(50);
  }

  public String get() {
    return tableName();
  }
}
