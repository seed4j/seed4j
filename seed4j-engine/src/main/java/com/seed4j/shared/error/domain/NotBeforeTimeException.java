package com.seed4j.shared.error.domain;

import java.time.Instant;

public final class NotBeforeTimeException extends AssertionException {

  private NotBeforeTimeException(String field, String message) {
    super(field, message);
  }

  public static NotBeforeTimeException.NotBeforeTimeExceptionBuilder field(String fieldName, Instant value) {
    return new NotBeforeTimeException.NotBeforeTimeExceptionBuilder(fieldName, value);
  }

  public record NotBeforeTimeExceptionBuilder(String fieldName, Instant value) {
    public NotBeforeTimeException strictlyNotBefore(Instant other) {
      return build("must be strictly before", other);
    }

    public NotBeforeTimeException notBefore(Instant other) {
      return build("must be before", other);
    }

    private NotBeforeTimeException build(String hint, Instant other) {
      return new NotBeforeTimeException(fieldName, message(fieldName, value, hint, other));
    }

    private static String message(String fieldName, Instant actual, String hint, Instant other) {
      return new StringBuilder()
        .append("Time in \"")
        .append(fieldName)
        .append("\" ")
        .append("having value : ")
        .append(actual)
        .append(' ')
        .append(hint)
        .append(" ")
        .append(other)
        .append(" but wasn't")
        .toString();
    }
  }

  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.NOT_BEFORE_TIME;
  }
}
