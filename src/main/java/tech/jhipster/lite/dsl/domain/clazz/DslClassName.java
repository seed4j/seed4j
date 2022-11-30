package tech.jhipster.lite.dsl.domain.clazz;

import org.springframework.util.StringUtils;
import tech.jhipster.lite.error.domain.Assert;

public record DslClassName(String name) {
  public DslClassName {
    Assert.field("name", name).noWhitespace().maxLength(50);
    name = StringUtils.capitalize(name);
  }

  public String get() {
    return name();
  }
}
