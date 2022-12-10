package tech.jhipster.lite.dsl.generator.java.clazz.domain.annotation;

import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;

public interface Annotation {
  String name();
  Optional<ClassImport> import_();
  Optional<String> value();
}
