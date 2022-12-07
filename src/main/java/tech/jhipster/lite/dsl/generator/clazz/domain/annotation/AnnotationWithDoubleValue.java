package tech.jhipster.lite.dsl.generator.clazz.domain.annotation;

import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.error.domain.Assert;

public record AnnotationWithDoubleValue(String name, Optional<String> value1, Optional<String> value2, Optional<ClassImport> import_)
  implements Annotation {
  public AnnotationWithDoubleValue {
    Assert.field("name", name).noWhitespace();
    Assert.notNull("value", name);
    Assert.notNull("import_", import_);
    value1.ifPresent(s -> Assert.field("value1", s).notBlank().noWhitespace());
    value2.ifPresent(s -> Assert.field("value2", s).notBlank().noWhitespace());
    import_.ifPresent(s -> Assert.field("import_", s.get()).notBlank().noWhitespace());
  }

  @Override
  public Optional<String> value() {
    return value1();
  }
}
