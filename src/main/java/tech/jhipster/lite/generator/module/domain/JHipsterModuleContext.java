package tech.jhipster.lite.generator.module.domain;

import java.util.HashMap;
import java.util.Map;
import tech.jhipster.lite.common.domain.JHipsterCollections;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.JHipsterModule.JHipsterModuleBuilder;

public class JHipsterModuleContext {

  private final Map<String, Object> context;

  private JHipsterModuleContext(JHipsterModuleContextBuilder builder) {
    context = JHipsterCollections.immutable(builder.context);
  }

  static JHipsterModuleContextBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleContextBuilder(module);
  }

  public Map<String, Object> get() {
    return context;
  }

  public static class JHipsterModuleContextBuilder {

    private final JHipsterModuleBuilder module;
    private final Map<String, Object> context = initContext();

    private JHipsterModuleContextBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    private Map<String, Object> initContext() {
      HashMap<String, Object> init = new HashMap<>();

      init.put("baseName", JHipsterProjectBaseName.DEFAULT_PROJECT_NAME);
      init.put("projectName", "JHipster Project");
      init.put("packageName", JHipsterBasePackage.DEFAULT_BASE_PACKAGE);
      init.put("prettierDefaultIndent", 2);

      return init;
    }

    public JHipsterModuleContextBuilder packageName(JHipsterBasePackage basePackage) {
      Assert.notNull("basePackage", basePackage);

      return put("packageName", basePackage.get());
    }

    public JHipsterModuleContextBuilder put(String key, Object value) {
      Assert.notBlank("key", key);
      Assert.notNull("value", value);

      context.put(key, value);

      return this;
    }

    public JHipsterModuleContext build() {
      return new JHipsterModuleContext(this);
    }

    public JHipsterModuleBuilder and() {
      return module;
    }
  }
}
