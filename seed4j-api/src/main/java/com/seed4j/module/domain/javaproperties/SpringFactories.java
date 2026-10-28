package com.seed4j.module.domain.javaproperties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;

public record SpringFactories(Collection<SpringFactory> factories) {
  public SpringFactories {
    Assert.field("factories", factories).notNull().noNullElement();
  }

  public Collection<SpringFactory> get() {
    return factories();
  }
}
