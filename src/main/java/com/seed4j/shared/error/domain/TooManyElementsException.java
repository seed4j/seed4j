package com.seed4j.shared.error.domain;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import org.jspecify.annotations.Nullable;

public final class TooManyElementsException extends AssertionException {

  private final String maxSize;
  private final String currentSize;

  private TooManyElementsException(TooManyElementsExceptionBuilder builder) {
    super(requireNonNull(builder.field), builder.message());
    maxSize = String.valueOf(builder.maxSize);
    currentSize = String.valueOf(builder.size);
  }

  public static TooManyElementsExceptionBuilder builder() {
    return new TooManyElementsExceptionBuilder();
  }

  public static class TooManyElementsExceptionBuilder {

    private @Nullable String field;
    private int maxSize;
    private int size;

    public TooManyElementsExceptionBuilder field(String field) {
      this.field = field;

      return this;
    }

    public TooManyElementsExceptionBuilder maxSize(int maxSize) {
      this.maxSize = maxSize;

      return this;
    }

    public TooManyElementsExceptionBuilder size(int size) {
      this.size = size;

      return this;
    }

    private String message() {
      return new StringBuilder()
        .append("Size of collection \"")
        .append(field)
        .append("\" must be at most ")
        .append(maxSize)
        .append(" but was ")
        .append(size)
        .toString();
    }

    public TooManyElementsException build() {
      return new TooManyElementsException(this);
    }
  }

  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.TOO_MANY_ELEMENTS;
  }

  @Override
  public Map<String, String> parameters() {
    return Map.of("maxSize", maxSize, "currentSize", currentSize);
  }
}
