package com.seed4j.module.domain.javaproperties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.List;

public record SpringComments(Collection<SpringComment> comments) {
  public static final SpringComments EMPTY = new SpringComments(List.of());

  public SpringComments {
    Assert.field("comments", comments).notNull().noNullElement();
  }

  public Collection<SpringComment> get() {
    return comments();
  }
}
