package tech.jhipster.lite.dsl.domain.config;

public record ConfigFluentMethod(boolean fluentMethod) {
  public static final boolean DEFAULT_VALUE = true;

  public boolean get() {
    return fluentMethod();
  }
}
