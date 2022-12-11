package tech.jhipster.lite.dsl.parser.domain.clazz.field;

import java.util.Optional;

public interface Constraint {
  String name();
  Optional<String> value();
}
