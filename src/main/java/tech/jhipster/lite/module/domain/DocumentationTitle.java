package tech.jhipster.lite.module.domain;

import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.shared.error.domain.Assert;

public record DocumentationTitle(String title) {
  private static final String SEPARATOR = "-";

  public DocumentationTitle {
    Assert.field("title", title).notBlank().maxLength(100);
  }

  public String filename() {
    return StringUtils.stripAccents(title.toLowerCase()).replaceAll("\\W", SEPARATOR).replaceAll("-+", SEPARATOR);
  }

  public String get() {
    return title();
  }
}
