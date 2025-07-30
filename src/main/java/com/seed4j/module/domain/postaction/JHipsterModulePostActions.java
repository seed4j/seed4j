package com.seed4j.module.domain.postaction;

import com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public final class JHipsterModulePostActions {

  private final Collection<RunnableInContext> actions;

  private JHipsterModulePostActions(JHipsterModulePostActionsBuilder builder) {
    actions = builder.actions;
  }

  public static JHipsterModulePostActionsBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModulePostActionsBuilder(module);
  }

  public void run(JHipsterModuleExecutionContext context) {
    Assert.notNull("context", context);

    actions.forEach(action -> action.run(context));
  }

  public static final class JHipsterModulePostActionsBuilder {

    private final JHipsterModuleBuilder module;
    private final Collection<RunnableInContext> actions = new ArrayList<>();

    private JHipsterModulePostActionsBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModulePostActionsBuilder add(Runnable action) {
      Assert.notNull("action", action);

      return add(context -> action.run());
    }

    public JHipsterModulePostActionsBuilder add(RunnableInContext action) {
      Assert.notNull("action", action);

      actions.add(action);

      return this;
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    public JHipsterModulePostActions build() {
      return new JHipsterModulePostActions(this);
    }
  }
}
