package com.seed4j.module.infrastructure.secondary.javadependency.gradle;

/**
 * Spaces the braces of the inline tables like the Prettier TOML plugin does ({ key = "value" }), so that a catalog written by
 * Seed4J is already formatted. Empty inline tables and the braces in strings are kept as they are.
 */
final class TomlInlineTables {

  private static final char QUOTE = '"';
  private static final char ESCAPE = '\\';
  private static final char OPENING_BRACE = '{';
  private static final char CLOSING_BRACE = '}';
  private static final String EMPTY_INLINE_TABLE = "{}";

  private TomlInlineTables() {}

  static String withBraceSpaces(String toml) {
    StringBuilder result = new StringBuilder(toml.length());
    boolean inString = false;

    for (int index = 0; index < toml.length(); index++) {
      char current = toml.charAt(index);

      if (inString && current == ESCAPE) {
        result.append(current).append(toml.charAt(++index));
        continue;
      }

      if (current == QUOTE) {
        inString = !inString;
      } else if (!inString && current == OPENING_BRACE && !toml.startsWith(EMPTY_INLINE_TABLE, index)) {
        result.append(current).append(' ');
        continue;
      } else if (!inString && current == CLOSING_BRACE && !toml.startsWith(EMPTY_INLINE_TABLE, index - 1)) {
        result.append(' ');
      }

      result.append(current);
    }

    return result.toString();
  }
}
