package tech.jhipster.lite.dsl.parser.domain.clazz.field;

import java.util.Optional;
import tech.jhipster.lite.error.domain.Assert;

public record ConstraintWithValue(String name, Optional<String> value) implements Constraint {
  public ConstraintWithValue {
    Assert.field("key", name).noWhitespace();
    Assert.notNull("value", name);
    value.ifPresent(s -> Assert.field("value", s).notNull());
  }
}
