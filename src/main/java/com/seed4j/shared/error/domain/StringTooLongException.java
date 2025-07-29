package com.seed4j.shared.error.domain;

import java.util.Map;

public final class StringTooLongException extends AssertionException {

  private final String maxLength;
  private final String currentLength;

  private StringTooLongException(StringTooLongExceptionBuilder builder) {
    super(builder.field, builder.message());
    maxLength = String.valueOf(builder.maxLength);
    currentLength = String.valueOf(builder.value.length());
  }

  public static StringTooLongExceptionBuilder builder() {
    return new StringTooLongExceptionBuilder();
  }

  public static final class StringTooLongExceptionBuilder {

    private String value;
    private int maxLength;
    private String field;

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

    private String message() {
      return new StringBuilder()
        .append("The value in field \"")
        .append(field)
        .append("\" must be at most ")
        .append(maxLength)
        .append(" long but was ")
        .append(value.length())
        .toString();
    }

    public StringTooLongException build() {
      return new StringTooLongException(this);
    }
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
