package tech.jhipster.lite.dsl.parser.domain;

import java.util.Collection;
import java.util.LinkedList;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.error.domain.Assert;

public class DslDomain {

  public static DslDomainBuilder dslDomainBuilder() {
    return new DslDomainBuilder();
  }

  private DslDomain() {}

  private Collection<DslClass> dslClasses;

  public Collection<DslClass> getDslClasses() {
    return dslClasses;
  }

  public static final class DslDomainBuilder {

    private Collection<DslClass> dslClasses = new LinkedList<>();

    private DslDomainBuilder() {}

    public DslDomainBuilder addDslClass(DslClass dslClass) {
      Assert.notNull("dslClass", dslClass);
      this.dslClasses.add(dslClass);
      return this;
    }

    public DslDomain build() {
      DslDomain dslDomain = new DslDomain();
      dslDomain.dslClasses = this.dslClasses;
      return dslDomain;
    }
  }
}
