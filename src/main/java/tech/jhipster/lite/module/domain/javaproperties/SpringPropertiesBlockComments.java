package tech.jhipster.lite.module.domain.javaproperties;

import java.util.Collection;
import tech.jhipster.lite.shared.error.domain.Assert;

public record SpringPropertiesBlockComments(Collection<SpringPropertiesBlockComment> propertiesBlockComments) {
  public SpringPropertiesBlockComments {
    Assert.field("propertiesBlockComments", propertiesBlockComments).notNull().noNullElement();
  }

  public Collection<SpringPropertiesBlockComment> get() {
    return propertiesBlockComments();
  }
}
