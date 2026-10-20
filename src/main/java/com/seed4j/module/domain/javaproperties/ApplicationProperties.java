package com.seed4j.module.domain.javaproperties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.List;

public record ApplicationProperties(Collection<ApplicationProperty> properties) {
  public static final ApplicationProperties EMPTY = new ApplicationProperties(List.of());

  public ApplicationProperties {
    Assert.field("properties", properties).notNull().noNullElement();
  }

  public Collection<ApplicationProperty> get() {
    return properties();
  }
}
