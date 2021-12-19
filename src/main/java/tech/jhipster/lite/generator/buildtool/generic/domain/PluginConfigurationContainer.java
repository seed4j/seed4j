package tech.jhipster.lite.generator.buildtool.generic.domain;

import java.util.List;

public class PluginConfigurationContainer implements PluginConfiguration {

  private String name;
  private List<PluginConfiguration> children;

  public PluginConfigurationContainer(String name, List<PluginConfiguration> children) {
    this.name = name;
    this.children = children;
  }

  public String getName() {
    return name;
  }

  public List<PluginConfiguration> getChildren() {
    return children;
  }
}
