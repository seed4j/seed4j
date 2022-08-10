package tech.jhipster.lite.module.domain.javaproperties;

import tech.jhipster.lite.error.domain.Assert;

public record PropertyComment(String comment) {
  public PropertyComment {
    Assert.notBlank("comment", comment);
  }

  public String get() {
    return comment();
  }
}
