package tech.jhipster.lite.dsl.generator.java.clazz.domain.enums;

import java.util.Optional;
import tech.jhipster.lite.error.domain.Assert;

public class EnumElementSimple {

  private String key;
  private String comment;

  public EnumElementSimple key(String key) {
    Assert.field("key", key).noWhitespace();
    this.key = key;
    return this;
  }

  public EnumElementSimple comment(String comment) {
    this.comment = comment;
    return this;
  }

  public String getKey() {
    return key;
  }

  public Optional<String> getComment() {
    return Optional.ofNullable(comment);
  }
}
