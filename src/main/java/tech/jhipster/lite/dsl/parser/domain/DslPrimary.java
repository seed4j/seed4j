package tech.jhipster.lite.dsl.parser.domain;

import java.util.Collection;
import java.util.LinkedList;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslEnum;
import tech.jhipster.lite.error.domain.Assert;

public class DslPrimary implements HasClassAndEnum {

  public static DslPrimaryBuilder builder() {
    return new DslPrimaryBuilder();
  }

  private DslPrimary() {}

  private Collection<DslClass> dslClasses;
  private Collection<DslEnum> dslEnum;

  private Collection<DslFrom> dslFroms;

  @Override
  public Collection<DslClass> getDslClasses() {
    return dslClasses;
  }

  @Override
  public Collection<DslEnum> getDslEnum() {
    return dslEnum;
  }

  public Collection<DslFrom> getDslFroms() {
    return dslFroms;
  }

  public static final class DslPrimaryBuilder implements ContainsClassBuilder {

    private final Collection<DslClass> dslClasses = new LinkedList<>();
    private final Collection<DslEnum> dslEnum = new LinkedList<>();
    private final Collection<DslFrom> dslFroms = new LinkedList<>();

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

    public DslPrimaryBuilder addDslFrom(DslFrom dslFrom) {
      Assert.notNull("dslFrom", dslFrom);
      this.dslFroms.add(dslFrom);
      return this;
    }

    public DslPrimary build() {
      DslPrimary dslPrimary = new DslPrimary();
      dslPrimary.dslClasses = this.dslClasses;
      dslPrimary.dslEnum = this.dslEnum;
      dslPrimary.dslFroms = this.dslFroms;
      return dslPrimary;
    }
  }
}
