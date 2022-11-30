package tech.jhipster.lite.dsl.domain;

import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.error.domain.Assert;

public record DslAnnotation(String name, Optional<String> value) {
  public DslAnnotation(String name, Optional<String> value) {
    Assert.field("name", name).notBlank();
    Assert.notNull("value", value);
    this.name = buildAnnotation(name);
    this.value = value;
  }

  private String buildAnnotation(String annotation) {
    return annotation.replace("@", "");
  }
}
