package tech.jhipster.lite.dsl.generator.java.clazz.domain.enums;

import java.util.Optional;
import tech.jhipster.lite.error.domain.Assert;

public class EnumElementValue {

  private String key;
  private String value;
  private String comment;

  public EnumElementValue(String key) {
    Assert.field("key", key).noWhitespace();
    this.key = key;
  }

  public EnumElementValue comment(String comment) {
    this.comment = comment;
    return this;
  }

  public EnumElementValue key(String key) {
    this.key = key;
    return this;
  }

  public EnumElementValue value(String value) {
    this.value = value;
    return this;
  }

  public String getKey() {
    return key;
  }

  public Optional<String> getValue() {
    return Optional.ofNullable(value);
  }

  public Optional<String> getComment() {
    return Optional.ofNullable(comment);
  }
}
