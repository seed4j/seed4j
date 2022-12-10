package tech.jhipster.lite.dsl.parser.domain.clazz.field;

import java.util.Objects;
import tech.jhipster.lite.error.domain.Assert;

public class FieldConstraint {

  public static final FieldConstraint REQUIRED = new FieldConstraint("required");
  public static final FieldConstraint UNIQUE = new FieldConstraint("unique");

  private String name;

  public FieldConstraint(String name) {
    Assert.field("key", name).notBlank();
    this.name = name;
  }

  public String name() {
    return name;
  }

  public String get() {
    return name();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FieldConstraint)) return false;
    FieldConstraint that = (FieldConstraint) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
