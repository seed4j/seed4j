package tech.jhipster.lite.dsl.generator.java.clazz.domain.annotation;

import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.error.domain.Assert;

public record AnnotationWithValue(String name, Optional<String> value, Optional<ClassImport> import_) implements Annotation {
  public AnnotationWithValue {
    Assert.field("key", name).noWhitespace();
    Assert.notNull("value", name);
    Assert.notNull("import_", import_);
    value.ifPresent(s -> Assert.field("value", s).notBlank().noWhitespace());
    import_.ifPresent(s -> Assert.field("import_", s.get()).notBlank().noWhitespace());
  }
  @Override
  public Optional<ClassImport> getImport() {
    return import_();
  }
}
