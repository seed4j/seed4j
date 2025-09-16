package com.seed4j.module.domain.postaction;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public final class Seed4JModulePostActions {

  private final Collection<RunnableInContext> actions;

  private Seed4JModulePostActions(Seed4JModulePostActionsBuilder builder) {
    actions = builder.actions;
  }

  public static Seed4JModulePostActionsBuilder builder(Seed4JModuleBuilder module) {
    return new Seed4JModulePostActionsBuilder(module);
  }

  public void run(Seed4JModuleExecutionContext context) {
    Assert.notNull("context", context);

    actions.forEach(action -> action.run(context));
  }

  public static final class Seed4JModulePostActionsBuilder {

    private final Seed4JModuleBuilder module;
    private final Collection<RunnableInContext> actions = new ArrayList<>();

    private Seed4JModulePostActionsBuilder(Seed4JModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public Seed4JModulePostActionsBuilder add(Runnable action) {
      Assert.notNull("action", action);

      return add(context -> action.run());
    }

    public Seed4JModulePostActionsBuilder add(RunnableInContext action) {
      Assert.notNull("action", action);

      actions.add(action);

      return this;
    }

    public Seed4JModuleBuilder and() {
      return module;
    }

    public Seed4JModulePostActions build() {
      return new Seed4JModulePostActions(this);
    }
  }
}
