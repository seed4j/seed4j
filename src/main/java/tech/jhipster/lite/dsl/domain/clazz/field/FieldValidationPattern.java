package tech.jhipster.lite.dsl.domain.clazz.field;

import java.util.Objects;
import java.util.regex.Pattern;
import tech.jhipster.lite.error.domain.Assert;

public class FieldValidationPattern extends FieldValidation {

  private Pattern pattern;

  public FieldValidationPattern(String name, Pattern pattern) {
    super(name);
    Assert.notNull("pattern", pattern);
    this.pattern = pattern;
  }

  public Pattern pattern() {
    return pattern;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), pattern);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FieldValidationPattern)) return false;
    FieldValidationPattern that = (FieldValidationPattern) o;
    return Objects.equals(name(), that.name());
  }
}
