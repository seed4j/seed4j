package tech.jhipster.lite.module.domain.replacement;

import tech.jhipster.lite.error.domain.Assert;

import java.util.function.BiFunction;

import static tech.jhipster.lite.module.domain.JHipsterModule.LINE_BREAK;

public record TextNeedleAfterReplacer(ReplacementCondition condition, String needle) implements ElementReplacer {
  public TextNeedleAfterReplacer {
    Assert.notNull("condition", condition);
    Assert.notBlank("text", needle);
  }

  @Override
  public boolean notMatchIn(String content) {
    Assert.notNull("content", content);

    return !content.contains(needle());
  }

  @Override
  public BiFunction<String, String, String> replacement() {
    return (content, replacement) -> {

      StringBuilder replacedContent = new StringBuilder(content);
      String replacementBlock = LINE_BREAK + replacement;

      int needleIndex = replacedContent.indexOf(needle());
      while (needleIndex != -1 ){
        int needleEolIndex = needleEndOfLine(replacedContent, needleIndex);
        replacedContent.insert(needleEolIndex, replacementBlock);

        needleIndex = replacedContent.indexOf(needle(), needleEolIndex + replacementBlock.length());
      }

      return replacedContent.toString();
    };
  }

  private int needleEndOfLine(StringBuilder sb, int needleIndex) {
    int eolIndex = sb.indexOf(LINE_BREAK, needleIndex);
    return eolIndex != -1 ? eolIndex : sb.length();
  }

  @Override
  public String searchMatcher() {
    return needle();
  }
}
