package com.seed4j.module.domain;

import com.seed4j.module.domain.JHipsterModule.SeedModuleBuilder;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public final class JHipsterModulePreActions {

  private final Collection<Runnable> actions;

  private JHipsterModulePreActions(JHipsterModulePreActionsBuilder builder) {
    actions = builder.actions;
  }

  public static JHipsterModulePreActionsBuilder builder(SeedModuleBuilder module) {
    return new JHipsterModulePreActionsBuilder(module);
  }

  public void run() {
    actions.forEach(Runnable::run);
  }

  public static final class JHipsterModulePreActionsBuilder {

    private final SeedModuleBuilder module;
    private final Collection<Runnable> actions = new ArrayList<>();

    private JHipsterModulePreActionsBuilder(SeedModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModulePreActionsBuilder add(Runnable action) {
      Assert.notNull("action", action);

      actions.add(action);

      return this;
    }

    public SeedModuleBuilder and() {
      return module;
    }

    public JHipsterModulePreActions build() {
      return new JHipsterModulePreActions(this);
    }
  }
}
