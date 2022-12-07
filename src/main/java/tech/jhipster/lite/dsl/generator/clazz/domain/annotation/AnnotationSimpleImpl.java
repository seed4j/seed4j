package tech.jhipster.lite.dsl.generator.clazz.domain.annotation;

import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.error.domain.Assert;

public record AnnotationSimpleImpl(String name, Optional<ClassImport> import_) implements Annotation {
  public AnnotationSimpleImpl {
    Assert.field("name", name).noWhitespace();
    Assert.notNull("import_", import_);
    import_.ifPresent(s -> Assert.field("import_", s.get()).notBlank().noWhitespace());
  }
  @Override
  public Optional<String> value() {
    return Optional.empty();
  }
}
