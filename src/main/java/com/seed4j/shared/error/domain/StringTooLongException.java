package com.seed4j.shared.error.domain;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import org.jspecify.annotations.Nullable;

public final class StringTooLongException extends AssertionException {

  private final String maxLength;
  private final String currentLength;

  private StringTooLongException(StringTooLongExceptionBuilder builder) {
    super(requireNonNull(builder.field), message(builder));
    Assert.notNull("value", builder.value);
    Assert.notNull("maxLength", builder.maxLength);
    maxLength = String.valueOf(builder.maxLength);
    currentLength = String.valueOf(builder.value.length());
  }

  public static StringTooLongExceptionBuilder builder() {
    return new StringTooLongExceptionBuilder();
  }

  public static final class StringTooLongExceptionBuilder {

    private @Nullable String value;
    private int maxLength;
    private @Nullable String field;

    private StringTooLongExceptionBuilder() {}

    StringTooLongExceptionBuilder field(String field) {
      this.field = field;

      return this;
    }

    StringTooLongExceptionBuilder value(String value) {
      this.value = value;

      return this;
    }

    StringTooLongExceptionBuilder maxLength(int maxLength) {
      this.maxLength = maxLength;

      return this;
    }

    public StringTooLongException build() {
      return new StringTooLongException(this);
    }
  }

  private static String message(StringTooLongExceptionBuilder builder) {
    Assert.notNull("value", builder.value);

    return new StringBuilder()
      .append("The value in field \"")
      .append(builder.field)
      .append("\" must be at most ")
      .append(builder.maxLength)
      .append(" long but was ")
      .append(builder.value.length())
      .toString();
  }

  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.STRING_TOO_LONG;
  }

  @Override
  public Map<String, String> parameters() {
    return Map.of("maxLength", maxLength, "currentLength", currentLength);
  }
}
