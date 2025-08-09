package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.shared.error.domain.Assert;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

public record RegexNeedleAfterReplacer(ReplacementCondition condition, Pattern pattern) implements ElementReplacer {
  public RegexNeedleAfterReplacer {
    Assert.notNull("condition", condition);
    Assert.notNull("pattern", pattern);
  }

  @Override
  public boolean notMatchIn(String content) {
    return !linePattern().matcher(content).find();
  }

  @Override
  public BiFunction<String, String, String> replacement() {
    return (content, replacement) ->
      linePattern()
        .matcher(content)
        .replaceAll(result -> result.group() + SeedModule.LINE_BREAK + escapeSpecialCharacters(replacement));
  }

  private String escapeSpecialCharacters(String replacement) {
    return replacement.replace("$", "\\$");
  }

  private Pattern linePattern() {
    String stringPattern = searchMatcher();

    if (isLinePattern(stringPattern)) {
      return pattern();
    }

    return Pattern.compile(stringPattern + ".*$", pattern().flags());
  }

  private boolean isLinePattern(String stringPattern) {
    return stringPattern.endsWith("$");
  }

  @Override
  public String searchMatcher() {
    return pattern().pattern();
  }
}
