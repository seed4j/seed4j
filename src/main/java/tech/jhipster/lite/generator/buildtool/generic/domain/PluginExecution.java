package tech.jhipster.lite.generator.buildtool.generic.domain;

import java.util.ArrayList;
import java.util.List;

public class PluginExecution {

  private String id;
  private String phase;
  private List<String> goals;
  private List<PluginConfiguration> configurations;

  private PluginExecution(PluginExecutionBuilder pluginExecutionBuilder) {
    this.id = pluginExecutionBuilder.id;
    this.phase = pluginExecutionBuilder.phase;
    this.goals = pluginExecutionBuilder.goals;
    this.configurations = pluginExecutionBuilder.configurations;
  }

  public static PluginExecutionBuilder builder() {
    return new PluginExecutionBuilder();
  }

  public String getId() {
    return id;
  }

  public String getPhase() {
    return phase;
  }

  public List<String> getGoals() {
    return goals;
  }

  public List<PluginConfiguration> getConfigurations() {
    return configurations;
  }

  public static class PluginExecutionBuilder {

    private String id;
    private String phase;
    private List<String> goals;
    private List<PluginConfiguration> configurations;

    public PluginExecutionBuilder id(String id) {
      this.id = id;
      return this;
    }

    public PluginExecutionBuilder phase(String phase) {
      this.phase = phase;
      return this;
    }

    public PluginExecutionBuilder goals(List<String> goals) {
      this.goals = goals;
      return this;
    }

    public PluginExecutionBuilder configuration(PluginConfiguration configuration) {
      if (this.configurations == null) this.configurations = new ArrayList<>();
      this.configurations.add(configuration);
      return this;
    }

    public PluginExecution build() {
      return new PluginExecution(this);
    }
  }
}
