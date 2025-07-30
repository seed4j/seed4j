package com.seed4j.module.domain.javaproperties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.List;

public record SpringProperties(Collection<SpringProperty> properties) {
  public static final SpringProperties EMPTY = new SpringProperties(List.of());

  public SpringProperties {
    Assert.field("properties", properties).notNull().noNullElement();
  }

  public Collection<SpringProperty> get() {
    return properties();
  }
}
