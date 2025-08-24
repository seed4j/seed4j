package com.seed4j.statistic.domain;

import com.seed4j.shared.error.domain.Assert;
import java.time.Instant;
import java.util.UUID;

public final class AppliedModule {

  private final AppliedModuleId id;
  private final Module module;
  private final Instant date;

  private AppliedModule(ModuleAppliedBuilder builder) {
    assertMandatoryFields(builder);

    id = builder.id;
    module = builder.module;
    date = builder.date;
  }

  private void assertMandatoryFields(ModuleAppliedBuilder builder) {
    Assert.notNull("id", builder.id);
    Assert.notNull("module", builder.module);
    Assert.notNull("date", builder.date);
  }

  public static ModuleAppliedIdBuilder builder() {
    return new ModuleAppliedBuilder();
  }

  public AppliedModuleId id() {
    return id;
  }

  public Module module() {
    return module;
  }

  public Instant date() {
    return date;
  }

  private static final class ModuleAppliedBuilder
    implements ModuleAppliedIdBuilder, ModuleAppliedModuleBuilder, ModuleAppliedApplicationDateBuilder {

    private AppliedModuleId id;
    private Module module;
    private Instant date;

    @Override
    public ModuleAppliedModuleBuilder id(AppliedModuleId id) {
      this.id = id;

      return this;
    }

    @Override
    public ModuleAppliedApplicationDateBuilder module(Module module) {
      this.module = module;

      return this;
    }

    @Override
    public AppliedModule date(Instant date) {
      this.date = date;

      return new AppliedModule(this);
    }
  }

  public interface ModuleAppliedIdBuilder {
    ModuleAppliedModuleBuilder id(AppliedModuleId id);

    default ModuleAppliedModuleBuilder id(UUID id) {
      return id(new AppliedModuleId(id));
    }
  }

  public interface ModuleAppliedModuleBuilder {
    ModuleAppliedApplicationDateBuilder module(Module module);

    default ModuleAppliedApplicationDateBuilder module(String module) {
      return module(new Module(module));
    }
  }

  public interface ModuleAppliedApplicationDateBuilder {
    AppliedModule date(Instant date);
  }
}
