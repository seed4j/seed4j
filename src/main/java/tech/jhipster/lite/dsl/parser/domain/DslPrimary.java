package tech.jhipster.lite.dsl.parser.domain;

import java.util.Collection;
import java.util.LinkedList;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslEnum;
import tech.jhipster.lite.error.domain.Assert;

public class DslPrimary {

  public static DslPrimaryBuilder builder() {
    return new DslPrimaryBuilder();
  }

  private DslPrimary() {}

  private Collection<DslClass> dslClasses;
  private Collection<DslEnum> dslEnum;

  public Collection<DslClass> getDslClasses() {
    return dslClasses;
  }

  public Collection<DslEnum> getDslEnum() {
    return dslEnum;
  }

  public static final class DslPrimaryBuilder {

    private final Collection<DslClass> dslClasses = new LinkedList<>();
    private final Collection<DslEnum> dslEnum = new LinkedList<>();

    private DslPrimaryBuilder() {}

    public DslPrimaryBuilder addDslClass(DslClass dslClass) {
      Assert.notNull("dslClass", dslClass);
      this.dslClasses.add(dslClass);
      return this;
    }

    public DslPrimaryBuilder addDslEnum(DslEnum dslEnum) {
      Assert.notNull("dslClass", dslEnum);
      this.dslEnum.add(dslEnum);
      return this;
    }

    public DslPrimary build() {
      DslPrimary dslDomain = new DslPrimary();
      dslDomain.dslClasses = this.dslClasses;
      dslDomain.dslEnum = this.dslEnum;
      return dslDomain;
    }
  }
}
