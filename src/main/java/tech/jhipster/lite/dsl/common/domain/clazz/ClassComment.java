package tech.jhipster.lite.dsl.common.domain.clazz;

import tech.jhipster.lite.dsl.parser.domain.ClassDslUtils;
import tech.jhipster.lite.error.domain.Assert;

public record ClassComment(String comment) {
  public ClassComment(String comment) {
    this.comment = cleanComment(comment);
  }

  private String cleanComment(String comment) {
    Assert.field("comment", comment).notBlank();
    return ClassDslUtils.cleanComment(comment);
  }
  public String get() {
    return comment();
  }
}
