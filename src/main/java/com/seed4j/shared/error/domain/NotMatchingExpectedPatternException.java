package com.seed4j.shared.error.domain;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.regex.Pattern;
import org.jspecify.annotations.Nullable;

public final class NotMatchingExpectedPatternException extends AssertionException {

  private final String pattern;

  private NotMatchingExpectedPatternException(NotMatchingExpectedPatternExceptionBuilder builder) {
    super(requireNonNull(builder.field), builder.message());
    Assert.notNull("pattern", builder.pattern);
    pattern = builder.pattern;
  }

  public static NotMatchingExpectedPatternExceptionBuilder builder() {
    return new NotMatchingExpectedPatternExceptionBuilder();
  }

  public static final class NotMatchingExpectedPatternExceptionBuilder {

    private @Nullable String value;
    private @Nullable String pattern;
    private @Nullable String field;

    private NotMatchingExpectedPatternExceptionBuilder() {}

    NotMatchingExpectedPatternExceptionBuilder field(String field) {
      this.field = field;

      return this;
    }

    NotMatchingExpectedPatternExceptionBuilder value(@Nullable String value) {
      this.value = value;

      return this;
    }

    NotMatchingExpectedPatternExceptionBuilder pattern(Pattern pattern) {
      this.pattern = pattern.pattern();

      return this;
    }

    private String message() {
      return "The value \"" + value + "\" in field \"" + field + "\" must match the pattern " + pattern;
    }

    public NotMatchingExpectedPatternException build() {
      return new NotMatchingExpectedPatternException(this);
    }
  }

  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.STRING_NOT_MATCHING_PATTERN;
  }

  @Override
  public Map<String, String> parameters() {
    return Map.of("pattern", pattern);
  }
}
