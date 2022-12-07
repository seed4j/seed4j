package tech.jhipster.lite.dsl.generator.clazz.domain.annotation;

import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.error.domain.Assert;

public record AnnotationSimple(String name, Optional<ClassImport> import_) implements Annotation {
  public AnnotationSimple {
    Assert.field("name", name).noWhitespace();
    Assert.notNull("import_", import_);
    import_.ifPresent(s -> Assert.field("import_", s.get()).notBlank().noWhitespace());
  }
  @Override
  public Optional<String> value() {
    return Optional.empty();
  }
}
