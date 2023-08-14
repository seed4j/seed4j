package tech.jhipster.lite.module.domain;

import java.util.ArrayList;
import java.util.Collection;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JHipsterModulePreActions {

  private final Collection<Runnable> actions;

  private JHipsterModulePreActions(JHipsterModulePreActionsBuilder builder) {
    actions = builder.actions;
  }

  public static JHipsterModulePreActionsBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModulePreActionsBuilder(module);
  }

  public void run() {
    actions.forEach(Runnable::run);
  }

  public static class JHipsterModulePreActionsBuilder {

    private final JHipsterModuleBuilder module;
    private final Collection<Runnable> actions = new ArrayList<>();

    private JHipsterModulePreActionsBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModulePreActionsBuilder add(Runnable action) {
      Assert.notNull("action", action);

      actions.add(action);

      return this;
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    public JHipsterModulePreActions build() {
      return new JHipsterModulePreActions(this);
    }
  }
}
