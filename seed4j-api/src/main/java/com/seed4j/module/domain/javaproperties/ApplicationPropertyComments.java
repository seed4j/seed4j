package com.seed4j.module.domain.javaproperties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.List;

public record ApplicationPropertyComments(Collection<ApplicationPropertyComment> comments) {
  public static final ApplicationPropertyComments EMPTY = new ApplicationPropertyComments(List.of());

  public ApplicationPropertyComments {
    Assert.field("comments", comments).notNull().noNullElement();
  }

  public Collection<ApplicationPropertyComment> get() {
    return comments();
  }
}
