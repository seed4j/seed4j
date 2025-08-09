package com.seed4j.module.domain.postaction;

import com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public final class SeedModulePostActions {

  private final Collection<RunnableInContext> actions;

  private SeedModulePostActions(SeedModulePostActionsBuilder builder) {
    actions = builder.actions;
  }

  public static SeedModulePostActionsBuilder builder(JHipsterModuleBuilder module) {
    return new SeedModulePostActionsBuilder(module);
  }

  public void run(SeedModuleExecutionContext context) {
    Assert.notNull("context", context);

    actions.forEach(action -> action.run(context));
  }

  public static final class SeedModulePostActionsBuilder {

    private final JHipsterModuleBuilder module;
    private final Collection<RunnableInContext> actions = new ArrayList<>();

    private SeedModulePostActionsBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public SeedModulePostActionsBuilder add(Runnable action) {
      Assert.notNull("action", action);

      return add(context -> action.run());
    }

    public SeedModulePostActionsBuilder add(RunnableInContext action) {
      Assert.notNull("action", action);

      actions.add(action);

      return this;
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    public SeedModulePostActions build() {
      return new SeedModulePostActions(this);
    }
  }
}
