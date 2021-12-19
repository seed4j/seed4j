package tech.jhipster.lite.generator.buildtool.generic.domain;

public class PluginConfigurationValue implements PluginConfiguration {

  private String name;
  private String value;

  public PluginConfigurationValue(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }
}
