package com.seed4j.shared.error.domain;

public class StringWithWhitespacesException extends AssertionException {

  public StringWithWhitespacesException(String field) {
    super(field, message(field));
  }

  private static String message(String field) {
    return "The field \"%s\" contains at least one space".formatted(field);
  }

  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.STRING_WITH_WHITESPACES;
  }
}
