package tech.jhipster.lite.module.domain.postaction;

import java.util.ArrayList;
import java.util.Collection;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JHipsterModulePostActions {

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

  public static class JHipsterModulePostActionsBuilder {

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
