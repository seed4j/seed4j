package tech.jhipster.lite.module.domain.replacement;

import java.util.function.BiFunction;
import java.util.regex.Pattern;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.shared.error.domain.Assert;

public record RegexNeedleBeforeReplacer(ReplacementCondition condition, Pattern pattern) implements ElementReplacer {
  public RegexNeedleBeforeReplacer {
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
      linePattern().matcher(content).replaceAll(result -> replacement + JHipsterModule.LINE_BREAK + result.group());
  }

  private Pattern linePattern() {
    String stringPattern = searchMatcher();

    if (isLinePattern(stringPattern)) {
      return pattern();
    }

    return Pattern.compile("^.*" + stringPattern, pattern().flags());
  }

  private boolean isLinePattern(String stringPattern) {
    return stringPattern.startsWith("^");
  }

  @Override
  public String searchMatcher() {
    return pattern().pattern();
  }
}
