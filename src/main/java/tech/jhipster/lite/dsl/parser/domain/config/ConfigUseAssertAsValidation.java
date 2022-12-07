package tech.jhipster.lite.dsl.parser.domain.config;

public record ConfigUseAssertAsValidation(boolean useAssert) {
  public static final boolean DEFAULT_VALUE = false;
  public static final ConfigUseAssertAsValidation DEFAULT = new ConfigUseAssertAsValidation(DEFAULT_VALUE);

  public ConfigUseAssertAsValidation(boolean useAssert) {
    this.useAssert = useAssert;
  }

  public boolean get() {
    return useAssert();
  }
}
