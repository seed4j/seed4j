package com.seed4j.module.domain;

import com.seed4j.module.domain.SeedModule.SeedModuleBuilder;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public final class SeedModulePreActions {

  private final Collection<Runnable> actions;

  private SeedModulePreActions(SeedModulePreActionsBuilder builder) {
    actions = builder.actions;
  }

  public static SeedModulePreActionsBuilder builder(SeedModuleBuilder module) {
    return new SeedModulePreActionsBuilder(module);
  }

  public void run() {
    actions.forEach(Runnable::run);
  }

  public static final class SeedModulePreActionsBuilder {

    private final SeedModuleBuilder module;
    private final Collection<Runnable> actions = new ArrayList<>();

    private SeedModulePreActionsBuilder(SeedModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public SeedModulePreActionsBuilder add(Runnable action) {
      Assert.notNull("action", action);

      actions.add(action);

      return this;
    }

    public SeedModuleBuilder and() {
      return module;
    }

    public SeedModulePreActions build() {
      return new SeedModulePreActions(this);
    }
  }
}
