package tech.jhipster.lite.shared.error.domain;

public class StringWithWitespacesException extends AssertionException {

  public StringWithWitespacesException(String field) {
    super(field, message(field));
  }

  private static String message(String field) {
    return new StringBuilder().append("The field \"").append(field).append("\" contains at least one space").toString();
  }

  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.STRING_WITH_WITESPACES;
  }
}
