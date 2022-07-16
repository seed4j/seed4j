package tech.jhipster.lite.error.domain;

public class StringWithWitespacesException extends AssertionException {

  public StringWithWitespacesException(String field) {
    super(message(field));
  }

  private static String message(String field) {
    return new StringBuilder().append("The field \"").append(field).append("\" contains at least one space").toString();
  }
}
