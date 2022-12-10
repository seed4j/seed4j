package tech.jhipster.lite.dsl.parser.domain.clazz.field;

import java.util.Optional;
import tech.jhipster.lite.error.domain.Assert;

public record ConstraintSimple(String name) implements Constraint {
  public ConstraintSimple {
    Assert.field("key", name).noWhitespace();
  }
  @Override
  public Optional<String> value() {
    return Optional.empty();
  }
}
