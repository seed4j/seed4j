package tech.jhipster.lite.dsl.parser.domain;

import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslEnum;

public interface ContainsClassBuilder {
  ContainsClassBuilder addDslClass(DslClass dslClass);

  ContainsClassBuilder addDslEnum(DslEnum dslEnum);
}
