package com.seed4j.shared.error.infrastructure.primary;

import java.util.Map;
import org.jspecify.annotations.Nullable;

final class ArgumentsReplacer {

  private static final String OPEN = "{{";
  private static final String CLOSE = "}}";

  private final Map<String, ?> arguments;

  private ArgumentsReplacer(Map<String, ?> arguments) {
    this.arguments = arguments;
  }

  public static @Nullable String replaceParameters(@Nullable String message, @Nullable Map<String, ?> arguments) {
    if (message == null || arguments == null) {
      return message;
    }

    return new ArgumentsReplacer(arguments).format(message);
  }

  private String format(String message) {
    var result = new StringBuilder(message);

    int lastMustaches = result.indexOf(OPEN);
    while (lastMustaches != -1) {
      int end = result.indexOf(CLOSE, lastMustaches);
      String key = result.substring(lastMustaches + OPEN.length(), end).trim();

      result.replace(lastMustaches, end + CLOSE.length(), getArgumentValue(key));

      lastMustaches = result.indexOf(OPEN, lastMustaches + 2);
    }

    return result.toString();
  }

  private String getArgumentValue(String key) {
    Object value = arguments.get(key);
    if (value == null) {
      return "{{ " + key + " }}";
    }

    return value.toString();
  }
}
