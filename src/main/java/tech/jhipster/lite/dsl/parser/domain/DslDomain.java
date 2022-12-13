package tech.jhipster.lite.dsl.parser.domain;

import java.util.Collection;
import java.util.LinkedList;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslEnum;
import tech.jhipster.lite.error.domain.Assert;

public class DslDomain {

  public static DslDomainBuilder dslDomainBuilder() {
    return new DslDomainBuilder();
  }

  private DslDomain() {}

  private Collection<DslClass> dslClasses;
  private Collection<DslEnum> dslEnum;

  public Collection<DslClass> getDslClasses() {
    return dslClasses;
  }

  public Collection<DslEnum> getDslEnum() {
    return dslEnum;
  }

  public static final class DslDomainBuilder {

    private Collection<DslClass> dslClasses = new LinkedList<>();
    private Collection<DslEnum> dslEnum = new LinkedList<>();

    private DslDomainBuilder() {}

    public DslDomainBuilder addDslClass(DslClass dslClass) {
      Assert.notNull("dslClass", dslClass);
      this.dslClasses.add(dslClass);
      return this;
    }

    public DslDomainBuilder addDslEnum(DslEnum dslEnum) {
      Assert.notNull("dslClass", dslEnum);
      this.dslEnum.add(dslEnum);
      return this;
    }

    public DslDomain build() {
      DslDomain dslDomain = new DslDomain();
      dslDomain.dslClasses = this.dslClasses;
      dslDomain.dslEnum = this.dslEnum;
      return dslDomain;
    }
  }
}
