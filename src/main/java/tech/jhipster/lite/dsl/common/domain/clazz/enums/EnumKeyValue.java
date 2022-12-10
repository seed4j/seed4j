package tech.jhipster.lite.dsl.common.domain.clazz.enums;

import java.util.Optional;
import tech.jhipster.lite.error.domain.Assert;

public class EnumKeyValue {

  public static EnumKeyValueBuilder builder() {
    return new EnumKeyValueBuilder();
  }

  private EnumKey key;
  private EnumValue value;

  private EnumComment comment;

  protected EnumKeyValue(EnumKey key) {
    Assert.notNull("enumKey", key);
    this.key = key;
  }

  protected EnumKeyValue(EnumKey key, EnumValue value) {
    this(key);
    this.value = value;
  }

  public EnumKey getKey() {
    return key;
  }

  public Optional<EnumValue> getValue() {
    return Optional.ofNullable(value);
  }

  public Optional<EnumComment> getComment() {
    return Optional.ofNullable(comment);
  }

  public static final class EnumKeyValueBuilder {

    private EnumComment comment;
    private EnumKey enumKey;
    private EnumValue enumValue;

    private EnumKeyValueBuilder() {}

    public EnumKeyValueBuilder comment(EnumComment comment) {
      this.comment = comment;
      return this;
    }

    public EnumKeyValueBuilder enumKey(EnumKey enumKey) {
      this.enumKey = enumKey;
      return this;
    }

    public EnumKeyValueBuilder enumValue(EnumValue enumValue) {
      this.enumValue = enumValue;
      return this;
    }

    public EnumKeyValue build() {
      EnumKeyValue enumKeyValue = new EnumKeyValue(enumKey, enumValue);
      enumKeyValue.comment = this.comment;
      return enumKeyValue;
    }
  }
}
