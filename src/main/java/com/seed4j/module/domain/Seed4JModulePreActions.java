package com.seed4j.module.domain;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public final class Seed4JModulePreActions {

  private final Collection<Runnable> actions;

  private Seed4JModulePreActions(Seed4JModulePreActionsBuilder builder) {
    actions = builder.actions;
  }

  public static Seed4JModulePreActionsBuilder builder(Seed4JModuleBuilder module) {
    return new Seed4JModulePreActionsBuilder(module);
  }

  public void run() {
    actions.forEach(Runnable::run);
  }

  public static final class Seed4JModulePreActionsBuilder {

    private final Seed4JModuleBuilder module;
    private final Collection<Runnable> actions = new ArrayList<>();

    private Seed4JModulePreActionsBuilder(Seed4JModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public Seed4JModulePreActionsBuilder add(Runnable action) {
      Assert.notNull("action", action);

      actions.add(action);

      return this;
    }

    public Seed4JModuleBuilder and() {
      return module;
    }

    public Seed4JModulePreActions build() {
      return new Seed4JModulePreActions(this);
    }
  }
}
