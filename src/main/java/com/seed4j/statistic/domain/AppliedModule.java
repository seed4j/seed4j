package com.seed4j.statistic.domain;

import com.seed4j.shared.error.domain.Assert;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public final class AppliedModule {

  private final AppliedModuleId id;
  private final ProjectPath path;
  private final Module module;
  private final Instant date;
  private final ModuleProperties properties;

  private AppliedModule(ModuleAppliedBuilder builder) {
    assertMandatoryFields(builder);

    id = builder.id;
    path = builder.path;
    module = builder.module;
    date = builder.date;
    properties = builder.properties;
  }

  private void assertMandatoryFields(ModuleAppliedBuilder builder) {
    Assert.notNull("id", builder.id);
    Assert.notNull("path", builder.path);
    Assert.notNull("module", builder.module);
    Assert.notNull("date", builder.date);
    Assert.notNull("properties", builder.properties);
  }

  public static ModuleAppliedIdBuilder builder() {
    return new ModuleAppliedBuilder();
  }

  public AppliedModuleId id() {
    return id;
  }

  public ProjectPath path() {
    return path;
  }

  public Module module() {
    return module;
  }

  public Instant date() {
    return date;
  }

  public ModuleProperties properties() {
    return properties;
  }

  private static final class ModuleAppliedBuilder
    implements
      ModuleAppliedIdBuilder,
      ModuleAppliedPathBuilder,
      ModuleAppliedModuleBuilder,
      ModuleAppliedApplicationDateBuilder,
      ModuleAppliedPropertiesBuilder {

    private AppliedModuleId id;
    private ProjectPath path;
    private Module module;
    private Instant date;
    private ModuleProperties properties;

    @Override
    public ModuleAppliedPathBuilder id(AppliedModuleId id) {
      this.id = id;

      return this;
    }

    @Override
    public ModuleAppliedModuleBuilder path(ProjectPath path) {
      this.path = path;

      return this;
    }

    @Override
    public ModuleAppliedApplicationDateBuilder module(Module module) {
      this.module = module;

      return this;
    }

    @Override
    public ModuleAppliedPropertiesBuilder date(Instant date) {
      this.date = date;

      return this;
    }

    @Override
    public AppliedModule properties(ModuleProperties properties) {
      this.properties = properties;

      return new AppliedModule(this);
    }
  }

  public interface ModuleAppliedIdBuilder {
    ModuleAppliedPathBuilder id(AppliedModuleId id);

    default ModuleAppliedPathBuilder id(UUID id) {
      return id(new AppliedModuleId(id));
    }
  }

  public interface ModuleAppliedPathBuilder {
    ModuleAppliedModuleBuilder path(ProjectPath path);

    default ModuleAppliedModuleBuilder path(String path) {
      return path(new ProjectPath(path));
    }
  }

  public interface ModuleAppliedModuleBuilder {
    ModuleAppliedApplicationDateBuilder module(Module module);

    default ModuleAppliedApplicationDateBuilder module(String module) {
      return module(new Module(module));
    }
  }

  public interface ModuleAppliedApplicationDateBuilder {
    ModuleAppliedPropertiesBuilder date(Instant date);
  }

  public interface ModuleAppliedPropertiesBuilder {
    AppliedModule properties(ModuleProperties properties);

    default AppliedModule properties(Map<String, Object> properties) {
      return properties(new ModuleProperties(properties));
    }
  }
}
