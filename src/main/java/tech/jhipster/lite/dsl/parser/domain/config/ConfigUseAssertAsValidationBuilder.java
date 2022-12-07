package tech.jhipster.lite.dsl.parser.domain.config;

public class ConfigUseAssertAsValidationBuilder {

  public static final ConfigUseAssertAsValidation DEFAULT = new ConfigUseAssertAsValidation(ConfigUseAssertAsValidation.DEFAULT_VALUE);

  public static ConfigUseAssertAsValidationBuilder.Builder builderAssertAsValidation() {
    return new ConfigUseAssertAsValidationBuilder.Builder();
  }

  private ConfigUseAssertAsValidationBuilder() {}

  public static final class Builder {

    private boolean useAssert = false;

    public ConfigUseAssertAsValidation build() {
      return new ConfigUseAssertAsValidation(useAssert);
    }

    public Builder useAssertAsValidation(boolean useAssert) {
      this.useAssert = useAssert;
      return this;
    }

    public Builder useAssertAsValidation(String fluentMethodStr) {
      if (fluentMethodStr == null) {
        fluentMethodStr = "";
      }
      return useAssertAsValidation(
        switch (fluentMethodStr.toLowerCase()) {
          case "yes", "true" -> true;
          case "no", "false" -> false;
          default -> ConfigUseAssertAsValidation.DEFAULT_VALUE;
        }
      );
    }
  }
}
