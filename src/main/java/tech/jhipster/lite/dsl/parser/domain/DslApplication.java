package tech.jhipster.lite.dsl.parser.domain;

import java.util.LinkedList;
import java.util.List;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;
import tech.jhipster.lite.error.domain.Assert;

public class DslApplication {

  public static DslApplicationBuilder dslApplilcationBuilder() {
    return new DslApplicationBuilder();
  }

  private DslApplication() {}

  private ConfigApp config;
  private List<DslContext> lstDslContext;

  public ConfigApp getConfig() {
    return config;
  }

  public List<DslContext> getLstDslContext() {
    return lstDslContext;
  }

  @Override
  public String toString() {
    return "DslApplication{" + "config=" + config + ", lstDslContext=" + lstDslContext + '}';
  }

  public static final class DslApplicationBuilder {

    private ConfigApp config;
    private List<DslContext> lstDslContext = new LinkedList<>();

    private DslApplicationBuilder() {}

    public DslApplicationBuilder config(ConfigApp config) {
      Assert.notNull("config", config);
      this.config = config;
      return this;
    }

    public DslApplicationBuilder addDslContext(DslContext context) {
      Assert.notNull("context", context);
      this.lstDslContext.add(context);
      return this;
    }

    public DslApplication build() {
      DslApplication dslApplication = new DslApplication();
      if (this.config == null) {
        this.config = ConfigApp.configBuilder().build();
      }
      dslApplication.config = this.config;

      dslApplication.lstDslContext = this.lstDslContext;
      return dslApplication;
    }
  }
}
