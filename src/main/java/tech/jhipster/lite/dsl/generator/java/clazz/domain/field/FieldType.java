package tech.jhipster.lite.dsl.generator.java.clazz.domain.field;

import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;

public interface FieldType {
  String name();
  Optional<ClassImport> getImport();
}
