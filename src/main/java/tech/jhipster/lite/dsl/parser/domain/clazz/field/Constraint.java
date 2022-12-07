package tech.jhipster.lite.dsl.parser.domain.clazz.field;

import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;

public interface Constraint {
  String name();
  Optional<String> value();
}
