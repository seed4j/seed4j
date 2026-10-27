package com.seed4j.module.domain;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.javabuild.JavaBuildTool;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.collection.domain.Seed4JCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class Seed4JModuleContext {

  private final Map<String, Object> context;

  private Seed4JModuleContext(Map<String, Object> context) {
    this.context = Seed4JCollections.immutable(context);
  }

  public static Seed4JModuleContext empty() {
    return new Seed4JModuleContext(new HashMap<>());
  }

  public Seed4JModuleContext withJavaBuildTool(JavaBuildTool javaBuildTool) {
    Map<String, Object> additionalValues = Map.of(
      Seed4JModuleProperties.JAVA_BUILD_TOOL,
      javaBuildTool.name().toLowerCase(Locale.ROOT),
      Seed4JModuleProperties.PROJECT_BUILD_DIRECTORY,
      javaBuildTool.buildDirectory().get()
    );
    return new Seed4JModuleContext(Seed4JCollections.concat(context, additionalValues));
  }

  public static Seed4JModuleContextBuilder builder(Seed4JModuleBuilder module) {
    return new Seed4JModuleContextBuilder(module);
  }

  public Map<String, Object> get() {
    return context;
  }

  public static final class Seed4JModuleContextBuilder {

    private final Seed4JModuleBuilder module;
    private final Map<String, Object> context;

    private Seed4JModuleContextBuilder(Seed4JModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
      context = initialContext(module.properties());
    }

    private Map<String, Object> initialContext(Seed4JModuleProperties properties) {
      Map<String, Object> init = new HashMap<>();

      init.put(Seed4JModuleProperties.PROJECT_BASE_NAME_PARAMETER, properties.projectBaseName().get());
      init.put(Seed4JModuleProperties.PROJECT_NAME_PARAMETER, properties.projectName().get());
      init.put(Seed4JModuleProperties.BASE_PACKAGE_PARAMETER, properties.basePackage().get());
      init.put(Seed4JModuleProperties.SERVER_PORT_PARAMETER, properties.serverPort().get());
      init.put(Seed4JModuleProperties.INDENTATION_PARAMETER, properties.indentation().spacesCount());
      init.put(Seed4JModuleProperties.JAVA_VERSION, properties.javaVersion().get());
      init.put(Seed4JModuleProperties.PROJECT_BUILD_DIRECTORY, JavaBuildTool.MAVEN.buildDirectory().get());
      init.put(Seed4JModuleProperties.SPRING_CONFIGURATION_FORMAT, properties.springConfigurationFormat().get());

      return init;
    }

    public Seed4JModuleContextBuilder put(String key, Object value) {
      Assert.notBlank("key", key);
      Assert.notNull("value", value);

      context.put(key, value);

      return this;
    }

    public Seed4JModuleContext build() {
      return new Seed4JModuleContext(this.context);
    }

    public Seed4JModuleBuilder and() {
      return module;
    }
  }
}
