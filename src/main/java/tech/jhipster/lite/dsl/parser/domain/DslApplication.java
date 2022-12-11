package tech.jhipster.lite.dsl.parser.domain;

import java.util.LinkedList;
import java.util.List;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

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
    private final List<DslContext> lstDslContext = new LinkedList<>();
    private JHipsterProjectFolder folder;

    private DslApplicationBuilder() {}

    public DslApplicationBuilder config(ConfigApp config) {
      Assert.notNull("config", config);
      this.config = config;
      return this;
    }

    public DslApplicationBuilder overrideProjectFolder(JHipsterProjectFolder folder) {
      Assert.notNull("folder", folder);
      this.folder = folder;
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
        ConfigApp.ConfigAppBuilder confBuilder = ConfigApp.configBuilder();
        if (folder != null) {
          confBuilder.projectFolder(folder.folder());
        }
        this.config = confBuilder.build();
      } else {
        if (folder != null) {
          ConfigApp.ConfigAppBuilder confBuilder = ConfigApp.configBuilder();
          confBuilder.from(this.config).projectFolder(folder.folder());
          this.config = confBuilder.build();
        }
      }

      dslApplication.config = this.config;

      dslApplication.lstDslContext = this.lstDslContext;
      return dslApplication;
    }
  }
}
