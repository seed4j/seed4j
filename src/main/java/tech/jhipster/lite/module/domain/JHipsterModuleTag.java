package tech.jhipster.lite.module.domain;

import java.util.regex.Pattern;
import tech.jhipster.lite.error.domain.Assert;

public record JHipsterModuleTag(String tag) {
  private static final Pattern TAG_FORMAT = Pattern.compile("^[a-z1-9-]+$");
  public JHipsterModuleTag {
    Assert.field("tag", tag).notNull().noWhitespace().maxLength(15);

    assertFormat(tag);
  }

  private static void assertFormat(String tag) {
    if (invalidFormat(tag)) {
      throw new InvalidJHipsterModuleTagException(tag);
    }
  }
  private static boolean invalidFormat(String slug) {
    return !TAG_FORMAT.matcher(slug).matches();
  }

  public String get() {
    return tag();
  }
}
