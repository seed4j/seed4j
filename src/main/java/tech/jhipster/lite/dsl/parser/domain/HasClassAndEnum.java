package tech.jhipster.lite.dsl.parser.domain;

import java.util.Collection;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslEnum;

public interface HasClassAndEnum {
  Collection<DslClass> getDslClasses();
  Collection<DslEnum> getDslEnum();
}
