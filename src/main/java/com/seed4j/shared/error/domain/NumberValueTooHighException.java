package com.seed4j.shared.error.domain;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import org.jspecify.annotations.Nullable;

public final class NumberValueTooHighException extends AssertionException {

  private final String max;
  private final String value;

  private NumberValueTooHighException(NumberValueTooHighExceptionBuilder builder) {
    super(requireNonNull(builder.field), builder.message());
    Assert.notNull("max", builder.maxValue);
    Assert.notNull("value", builder.value);
    max = builder.maxValue;
    value = builder.value;
  }

  public static NumberValueTooHighExceptionBuilder builder() {
    return new NumberValueTooHighExceptionBuilder();
  }

  public static class NumberValueTooHighExceptionBuilder {

    private @Nullable String field;
    private @Nullable String maxValue;
    private @Nullable String value;

    public NumberValueTooHighExceptionBuilder field(String field) {
      this.field = field;

      return this;
    }

    public NumberValueTooHighExceptionBuilder maxValue(String maxValue) {
      this.maxValue = maxValue;

      return this;
    }

    public NumberValueTooHighExceptionBuilder value(String value) {
      this.value = value;

      return this;
    }

    public String message() {
      return new StringBuilder()
        .append("Value of field \"")
        .append(field)
        .append("\" must be at most ")
        .append(maxValue)
        .append(" but was ")
        .append(value)
        .toString();
    }

    public NumberValueTooHighException build() {
      return new NumberValueTooHighException(this);
    }
  }

  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.NUMBER_VALUE_TOO_HIGH;
  }

  @Override
  public Map<String, String> parameters() {
    return Map.of("max", max, "value", value);
  }
}
