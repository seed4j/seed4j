package tech.jhipster.lite.dsl.domain;

import tech.jhipster.lite.dsl.domain.clazz.DslContextName;
import tech.jhipster.lite.error.domain.Assert;

public class DslContext {

  public static DslContextBuilder dslContextBuilder() {
    return new DslContextBuilder();
  }

  private DslContext() {}

  private DslContextName name;
  private DslDomain domain;

  public DslContextName getName() {
    return name;
  }

  public DslDomain getDomain() {
    return domain;
  }

  public static final class DslContextBuilder {

    private DslContextName name;
    private DslDomain domain;

    private DslContextBuilder() {}

    public DslContextBuilder name(String name) {
      this.name = new DslContextName(name);
      return this;
    }

    public DslContextBuilder addDomain(DslDomain dslDomain) {
      Assert.notNull("dslDomain", dslDomain);
      this.domain = dslDomain;
      return this;
    }

    public DslContext build() {
      Assert.notNull("domain", this.domain);
      DslContext dslContext = new DslContext();
      dslContext.name = this.name;
      dslContext.domain = this.domain;
      return dslContext;
    }
  }
}
