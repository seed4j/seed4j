package com.seed4j.module.domain.properties;

import static java.util.Objects.requireNonNull;

import com.seed4j.shared.error.domain.GeneratorException;
import org.jspecify.annotations.Nullable;

final class InvalidPropertyTypeException extends GeneratorException {

  private InvalidPropertyTypeException(InvalidPropertyTypeExceptionBuilder builder) {
    super(
      badRequest(PropertiesErrorKey.INVALID_PROPERTY_TYPE)
        .message(buildMessage(builder))
        .addParameter("propertyKey", requireNonNull(builder.key))
        .addParameter("expectedType", requireNonNull(builder.expectedType).getName())
        .addParameter("actualType", requireNonNull(builder.actualType).getName())
    );
  }

  private static String buildMessage(InvalidPropertyTypeExceptionBuilder builder) {
    return new StringBuilder()
      .append("Can't get property ")
      .append(builder.key)
      .append(", expecting ")
      .append(builder.expectedType)
      .append(" but is a ")
      .append(builder.actualType)
      .toString();
  }

  static InvalidPropertyTypeExceptionKeyBuilder builder() {
    return new InvalidPropertyTypeExceptionBuilder();
  }

  static class InvalidPropertyTypeExceptionBuilder
    implements
      InvalidPropertyTypeExceptionKeyBuilder,
      InvalidPropertyTypeExceptionExpectedTypeBuilder,
      InvalidPropertyTypeExceptionActualTypeBuilder {

    private @Nullable String key;
    private @Nullable Class<?> expectedType;
    private @Nullable Class<?> actualType;

    @Override
    public InvalidPropertyTypeExceptionExpectedTypeBuilder key(String key) {
      this.key = key;

      return this;
    }

    @Override
    public InvalidPropertyTypeExceptionBuilder expectedType(Class<?> expectedType) {
      this.expectedType = expectedType;

      return this;
    }

    @Override
    public InvalidPropertyTypeException actualType(Class<?> actualType) {
      this.actualType = actualType;

      return new InvalidPropertyTypeException(this);
    }
  }

  interface InvalidPropertyTypeExceptionKeyBuilder {
    InvalidPropertyTypeExceptionExpectedTypeBuilder key(String key);
  }

  interface InvalidPropertyTypeExceptionExpectedTypeBuilder {
    InvalidPropertyTypeExceptionBuilder expectedType(Class<?> expectedType);
  }

  interface InvalidPropertyTypeExceptionActualTypeBuilder {
    InvalidPropertyTypeException actualType(Class<?> actualType);
  }
}
