package tech.jhipster.lite.dsl.parser.domain;

import java.util.LinkedList;
import java.util.List;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslContextName;
import tech.jhipster.lite.error.domain.Assert;

public class DslContext {

  public static DslContextBuilder dslContextBuilder() {
    return new DslContextBuilder();
  }

  private DslContext() {}

  private DslContextName name;
  private DslDomain domain;
  private List<DslPrimary> primaries;
  private List<DslSecondary> secondaries;

  public DslContextName getName() {
    return name;
  }

  public DslDomain getDomain() {
    return domain;
  }

  public List<DslPrimary> getPrimaries() {
    return primaries;
  }

  public static final class DslContextBuilder {

    private DslContextName name;
    private DslDomain domain;
    private final List<DslPrimary> primaries = new LinkedList<>();
    private final List<DslSecondary> secondaries = new LinkedList<>();

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

    public DslContextBuilder addPrimary(DslPrimary dslPrimary) {
      Assert.notNull("dslPrimary", dslPrimary);
      this.primaries.add(dslPrimary);
      return this;
    }

    public DslContextBuilder addSecondary(DslSecondary dslSecondary) {
      Assert.notNull("dslSecondary", dslSecondary);
      this.secondaries.add(dslSecondary);
      return this;
    }

    public DslContext build() {
      Assert.notNull("domain", this.domain);
      DslContext dslContext = new DslContext();
      dslContext.name = this.name;
      dslContext.domain = this.domain;
      dslContext.primaries = this.primaries;
      dslContext.secondaries = this.secondaries;
      return dslContext;
    }
  }
}
