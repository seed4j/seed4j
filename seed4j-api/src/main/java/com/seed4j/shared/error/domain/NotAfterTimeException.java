package com.seed4j.shared.error.domain;

import java.time.Instant;

public final class NotAfterTimeException extends AssertionException {

  private NotAfterTimeException(String field, String message) {
    super(field, message);
  }

  public static NotAfterTimeExceptionBuilder field(String fieldName, Instant value) {
    return new NotAfterTimeExceptionBuilder(fieldName, value);
  }

  public record NotAfterTimeExceptionBuilder(String fieldName, Instant value) {
    public NotAfterTimeException strictlyNotAfter(Instant other) {
      return build("must be strictly after", other);
    }

    public NotAfterTimeException notAfter(Instant other) {
      return build("must be after", other);
    }

    private NotAfterTimeException build(String hint, Instant other) {
      return new NotAfterTimeException(fieldName, message(fieldName, value, hint, other));
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
    return AssertionErrorType.NOT_AFTER_TIME;
  }
}
