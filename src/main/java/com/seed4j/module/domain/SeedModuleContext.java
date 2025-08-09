package com.seed4j.module.domain;

import com.seed4j.module.domain.SeedModule.SeedModuleBuilder;
import com.seed4j.module.domain.javabuild.JavaBuildTool;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class SeedModuleContext {

  private final Map<String, Object> context;

  private SeedModuleContext(Map<String, Object> context) {
    this.context = SeedCollections.immutable(context);
  }

  public static SeedModuleContext empty() {
    return new SeedModuleContext(new HashMap<>());
  }

  public SeedModuleContext withJavaBuildTool(JavaBuildTool javaBuildTool) {
    Map<String, Object> additionalValues = Map.of(
      SeedModuleProperties.JAVA_BUILD_TOOL,
      javaBuildTool.name().toLowerCase(Locale.ROOT),
      SeedModuleProperties.PROJECT_BUILD_DIRECTORY,
      javaBuildTool.buildDirectory().get()
    );
    return new SeedModuleContext(SeedCollections.concat(context, additionalValues));
  }

  public static JHipsterModuleContextBuilder builder(SeedModuleBuilder module) {
    return new JHipsterModuleContextBuilder(module);
  }

  public Map<String, Object> get() {
    return context;
  }

  public static final class JHipsterModuleContextBuilder {

    private final SeedModuleBuilder module;
    private final Map<String, Object> context;

    private JHipsterModuleContextBuilder(SeedModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
      context = initialContext(module.properties());
    }

    private Map<String, Object> initialContext(SeedModuleProperties properties) {
      Map<String, Object> init = new HashMap<>();

      init.put(SeedModuleProperties.PROJECT_BASE_NAME_PARAMETER, properties.projectBaseName().get());
      init.put(SeedModuleProperties.PROJECT_NAME_PARAMETER, properties.projectName().get());
      init.put(SeedModuleProperties.BASE_PACKAGE_PARAMETER, properties.basePackage().get());
      init.put(SeedModuleProperties.SERVER_PORT_PARAMETER, properties.serverPort().get());
      init.put(SeedModuleProperties.INDENTATION_PARAMETER, properties.indentation().spacesCount());
      init.put(SeedModuleProperties.JAVA_VERSION, properties.javaVersion().get());
      init.put(SeedModuleProperties.PROJECT_BUILD_DIRECTORY, JavaBuildTool.MAVEN.buildDirectory().get());
      init.put(SeedModuleProperties.SPRING_CONFIGURATION_FORMAT, properties.springConfigurationFormat().get());

      return init;
    }

    public JHipsterModuleContextBuilder put(String key, Object value) {
      Assert.notBlank("key", key);
      Assert.notNull("value", value);

      context.put(key, value);

      return this;
    }

    public SeedModuleContext build() {
      return new SeedModuleContext(this.context);
    }

    public SeedModuleBuilder and() {
      return module;
    }
  }
}
