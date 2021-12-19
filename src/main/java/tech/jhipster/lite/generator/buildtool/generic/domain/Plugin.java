package tech.jhipster.lite.generator.buildtool.generic.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import tech.jhipster.lite.error.domain.Assert;

public class Plugin {

  private final String groupId;
  private final String artifactId;
  private final Optional<String> version;
  private List<PluginExecution> executions;
  private List<PluginConfiguration> configurations;

  private Plugin(PluginBuilder builder) {
    Assert.notBlank("groupId", builder.groupId);
    Assert.notBlank("artifactId", builder.artifactId);

    this.groupId = builder.groupId;
    this.artifactId = builder.artifactId;
    this.version = optionalNotBlank(builder.version);
    this.executions = builder.executions;
    this.configurations = builder.configurations;
  }

  private Optional<String> optionalNotBlank(String value) {
    if (value == null || value.isBlank()) {
      return Optional.empty();
    }
    return Optional.of(value);
  }

  public static PluginBuilder builder() {
    return new PluginBuilder();
  }

  public String getGroupId() {
    return groupId;
  }

  public String getArtifactId() {
    return artifactId;
  }

  public Optional<String> getVersion() {
    return version;
  }

  public List<PluginExecution> getExecutions() {
    return executions;
  }

  public List<PluginConfiguration> getConfigurations() {
    return configurations;
  }

  public static class PluginBuilder {

    private String groupId;
    private String artifactId;
    private String version;
    private List<PluginExecution> executions;
    private List<PluginConfiguration> configurations;

    public PluginBuilder groupId(String groupId) {
      this.groupId = groupId;
      return this;
    }

    public PluginBuilder artifactId(String artifactId) {
      this.artifactId = artifactId;
      return this;
    }

    public PluginBuilder version(String version) {
      this.version = version;
      return this;
    }

    public PluginBuilder execution(PluginExecution execution) {
      if (executions == null) executions = new ArrayList<>();
      executions.add(execution);
      return this;
    }

    public PluginBuilder configuration(PluginConfiguration configuration) {
      if (configurations == null) configurations = new ArrayList<>();
      configurations.add(configuration);
      return this;
    }

    public Plugin build() {
      return new Plugin(this);
    }
  }
}
