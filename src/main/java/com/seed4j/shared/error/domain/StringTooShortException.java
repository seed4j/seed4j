package com.seed4j.shared.error.domain;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import org.jspecify.annotations.Nullable;

public final class StringTooShortException extends AssertionException {

  private final String minLength;
  private final String currentLength;

  private StringTooShortException(StringTooShortExceptionBuilder builder) {
    super(requireNonNull(builder.field), message(builder));
    Assert.notNull("value", builder.value);
    Assert.notNull("minLength", builder.minLength);
    minLength = String.valueOf(builder.minLength);
    currentLength = String.valueOf(builder.value.length());
  }

  public static StringTooShortExceptionBuilder builder() {
    return new StringTooShortExceptionBuilder();
  }

  public static final class StringTooShortExceptionBuilder {

    private @Nullable String value;
    private int minLength;
    private @Nullable String field;

    private StringTooShortExceptionBuilder() {}

    StringTooShortExceptionBuilder field(String field) {
      this.field = field;

      return this;
    }

    StringTooShortExceptionBuilder value(String value) {
      this.value = value;

      return this;
    }

    StringTooShortExceptionBuilder minLength(int minLength) {
      this.minLength = minLength;

      return this;
    }

    public StringTooShortException build() {
      return new StringTooShortException(this);
    }
  }

  private static String message(StringTooShortExceptionBuilder builder) {
    Assert.notNull("value", builder.value);

    return new StringBuilder()
      .append("The value in field \"")
      .append(builder.field)
      .append("\" must be at least ")
      .append(builder.minLength)
      .append(" long but was only ")
      .append(builder.value.length())
      .toString();
  }

  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.STRING_TOO_SHORT;
  }

  @Override
  public Map<String, String> parameters() {
    return Map.of("minLength", minLength, "currentLength", currentLength);
  }
}
