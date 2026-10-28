package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

/**
 * {@link ElementReplacer} that inserts content at the end of the file if the provided condition is met
 * @param condition that must be met to insert content at the end of the file
 */
public record EndOfFileReplacer(ReplacementCondition condition) implements ElementReplacer {
  private static final Pattern EOF_PATTERN = Pattern.compile("\\z", Pattern.MULTILINE);

  public EndOfFileReplacer {
    Assert.notNull("condition", condition);
    Assert.notNull("pattern", EOF_PATTERN);
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage(reason = "Pattern always matches")
  public boolean notMatchIn(String content) {
    return !EOF_PATTERN.matcher(content).find();
  }

  @Override
  public BiFunction<String, String, String> replacement() {
    return (content, replacement) ->
      EOF_PATTERN.matcher(content).replaceAll(result -> escapeSpecialCharacters(replacement) + Seed4JModule.LINE_BREAK + result.group());
  }

  private String escapeSpecialCharacters(String replacement) {
    return replacement.replace("$", "\\$");
  }

  @Override
  public String searchMatcher() {
    return EOF_PATTERN.pattern();
  }
}
