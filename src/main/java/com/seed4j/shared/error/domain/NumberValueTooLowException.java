package com.seed4j.shared.error.domain;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import org.jspecify.annotations.Nullable;

public final class NumberValueTooLowException extends AssertionException {

  private final String min;
  private final String value;

  private NumberValueTooLowException(NumberValueTooLowExceptionBuilder builder) {
    super(requireNonNull(builder.field), builder.message());
    Assert.notNull("max", builder.minValue);
    Assert.notNull("value", builder.value);
    min = builder.minValue;
    value = builder.value;
  }

  public static NumberValueTooLowExceptionBuilder builder() {
    return new NumberValueTooLowExceptionBuilder();
  }

  public static class NumberValueTooLowExceptionBuilder {

    private @Nullable String field;
    private @Nullable String minValue;
    private @Nullable String value;

    public NumberValueTooLowExceptionBuilder field(String field) {
      this.field = field;

      return this;
    }

    public NumberValueTooLowExceptionBuilder minValue(String minValue) {
      this.minValue = minValue;

      return this;
    }

    public NumberValueTooLowExceptionBuilder value(String value) {
      this.value = value;

      return this;
    }

    public String message() {
      return new StringBuilder()
        .append("Value of field \"")
        .append(field)
        .append("\" must be at least ")
        .append(minValue)
        .append(" but was ")
        .append(value)
        .toString();
    }

    public NumberValueTooLowException build() {
      return new NumberValueTooLowException(this);
    }
  }

  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.NUMBER_VALUE_TOO_LOW;
  }

  @Override
  public Map<String, String> parameters() {
    return Map.of("min", min, "value", value);
  }
}
