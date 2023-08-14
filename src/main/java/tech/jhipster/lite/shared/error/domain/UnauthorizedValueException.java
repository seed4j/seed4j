package tech.jhipster.lite.shared.error.domain;

public class UnauthorizedValueException extends RuntimeException {

  public UnauthorizedValueException(String field) {
    this(new RuntimeException(defaultMessage(field)));
  }

  public UnauthorizedValueException(RuntimeException runtimeException) {
    super(runtimeException.getMessage());
  }

  private static String defaultMessage(String field) {
    return "The field \"" + field + "\" was given an unauthorized value";
  }

  public static UnauthorizedValueException forNegativeValue(String field) {
    return new UnauthorizedValueException(new RuntimeException(defaultMessage(field) + " (negative)"));
  }

  public static UnauthorizedValueException forInconsistentValue(String field, Object input) {
    return new UnauthorizedValueException(new RuntimeException(defaultMessage(field) + " (inconsistent : " + input.toString() + ")"));
  }

  public static UnauthorizedValueException forInconsistentValues(String field1, Object input1, String field2, Object input2) {
    return new UnauthorizedValueException(
      new RuntimeException(defaultMessage(field2) + " (" + input2 + " which is inconsistent with \"" + field1 + "\" : " + input1 + ")")
    );
  }

  public static UnauthorizedValueException forUnequalValues(String field1, String field2, Object input1, Object input2) {
    return new UnauthorizedValueException(
      new RuntimeException(defaultMessage(field2) + " (" + input2 + " not equal with field \"" + field1 + "\" : " + input1.toString() + ")")
    );
  }

  public static UnauthorizedValueException forUnexpectedType(String field) {
    return new UnauthorizedValueException(new RuntimeException(defaultMessage(field) + " (unexpected type)"));
  }

  public static UnauthorizedValueException forUnknownValue(String field) {
    return new UnauthorizedValueException(new RuntimeException(defaultMessage(field) + " (unknown)"));
  }

  public static UnauthorizedValueException forTooShortValue(String field) {
    return new UnauthorizedValueException(new RuntimeException(defaultMessage(field) + " (too short)"));
  }

  public static UnauthorizedValueException forUnexpectedValue(String field, Object input, Object expectedValue) {
    return new UnauthorizedValueException(
      new RuntimeException(defaultMessage(field) + " (expected : \"" + expectedValue + "\", found : \"" + input.toString() + "\")")
    );
  }
}
