package tech.jhipster.lite.dsl.generator.clazz.domain;

public record FieldValidationNotNull() implements Annotation {
  @Override
  public String name() {
    return "NotNull";
  }
}
