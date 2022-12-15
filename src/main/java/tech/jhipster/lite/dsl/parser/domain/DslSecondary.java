package tech.jhipster.lite.dsl.parser.domain;

import java.util.Collection;
import java.util.LinkedList;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslEnum;
import tech.jhipster.lite.error.domain.Assert;

public class DslSecondary {

  public static DslSecondaryBuilder builder() {
    return new DslSecondaryBuilder();
  }

  private DslSecondary() {}

  private Collection<DslClass> dslClasses;
  private Collection<DslEnum> dslEnum;

  public Collection<DslClass> getDslClasses() {
    return dslClasses;
  }

  public Collection<DslEnum> getDslEnum() {
    return dslEnum;
  }

  public static final class DslSecondaryBuilder implements ContainsClassBuilder {

    private final Collection<DslClass> dslClasses = new LinkedList<>();
    private final Collection<DslEnum> dslEnum = new LinkedList<>();

    private DslSecondaryBuilder() {}

    public DslSecondaryBuilder addDslClass(DslClass dslClass) {
      Assert.notNull("dslClass", dslClass);
      this.dslClasses.add(dslClass);
      return this;
    }

    public DslSecondaryBuilder addDslEnum(DslEnum dslEnum) {
      Assert.notNull("dslClass", dslEnum);
      this.dslEnum.add(dslEnum);
      return this;
    }

    public DslSecondary build() {
      DslSecondary dslDomain = new DslSecondary();
      dslDomain.dslClasses = this.dslClasses;
      dslDomain.dslEnum = this.dslEnum;
      return dslDomain;
    }
  }
}
