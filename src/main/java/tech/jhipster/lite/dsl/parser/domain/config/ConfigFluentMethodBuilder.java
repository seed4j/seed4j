package tech.jhipster.lite.dsl.parser.domain.config;

public class ConfigFluentMethodBuilder {

  public static final ConfigFluentMethod DEFAULT = new ConfigFluentMethod(ConfigFluentMethod.DEFAULT_VALUE);

  public static ConfigFluentMethodBuilder.Builder builderFluentMethod() {
    return new ConfigFluentMethodBuilder.Builder();
  }

  private ConfigFluentMethodBuilder() {}

  public static final class Builder {

    private boolean fluentMethod = true;

    public ConfigFluentMethod build() {
      return new ConfigFluentMethod(fluentMethod);
    }

    public Builder fluentMethod(boolean fluentMethod) {
      this.fluentMethod = fluentMethod;
      return this;
    }

    public Builder fluentMethod(String fluentMethodStr) {
      if (fluentMethodStr == null) {
        fluentMethodStr = "";
      }
      fluentMethod(
        switch (fluentMethodStr.toLowerCase()) {
          case "yes", "true" -> true;
          case "no", "false" -> false;
          default -> true;
        }
      );
      return this;
    }
  }
}
