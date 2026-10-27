package com.seed4j.shared.error.domain;

public class NullElementInCollectionException extends AssertionException {

  public NullElementInCollectionException(String field) {
    super(field, message(field));
  }

  private static String message(String field) {
    return "The field \"%s\" contains a null element".formatted(field);
  }

  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.NULL_ELEMENT_IN_COLLECTION;
  }
}
