package tech.jhipster.lite.module.domain.javaproperties;

import java.util.Collection;
import tech.jhipster.lite.shared.error.domain.Assert;

public record SpringComments(Collection<SpringComment> comments) {
  public SpringComments {
    Assert.field("comments", comments).notNull().noNullElement();
  }

  public Collection<SpringComment> get() {
    return comments();
  }
}
