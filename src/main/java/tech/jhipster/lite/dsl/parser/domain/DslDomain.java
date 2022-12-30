package tech.jhipster.lite.dsl.parser.domain;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslEnum;
import tech.jhipster.lite.error.domain.Assert;

public class DslDomain {

  public static DslDomainBuilder builder() {
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

  public Optional<DslEnum> getEnum(String name) {
    Assert.field("name", name).notBlank().notNull();

    return dslEnum.stream().filter(dslEnum -> name.equalsIgnoreCase(dslEnum.getName().get())).findFirst();
  }

  public Optional<DslClass> getClass(String name) {
    Assert.field("name", name).notBlank().notNull();

    return dslClasses.stream().filter(dslClass -> name.equalsIgnoreCase(dslClass.getName().get())).findFirst();
  }

  public static final class DslDomainBuilder implements ContainsClassBuilder {

    private final Collection<DslClass> dslClasses = new LinkedList<>();
    private final Collection<DslEnum> dslEnum = new LinkedList<>();

    private DslDomainBuilder() {}

    @Override
    public DslDomainBuilder addDslClass(DslClass dslClass) {
      Assert.notNull("dslClass", dslClass);
      this.dslClasses.add(dslClass);
      return this;
    }

    @Override
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
