package tech.jhipster.lite.dsl.domain.clazz.field;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.lang.model.element.Modifier;
import tech.jhipster.lite.error.domain.Assert;

public class ClassField {

  public static FieldBuilder fieldBuilder() {
    return new FieldBuilder();
  }

  private ClassField() {}

  private FieldName name;
  private List<Modifier> modifiers;
  private Optional<FieldComment> comment;
  private FieldType type;
  private List<FieldValidation> validations;
  private List<FieldValidator> validators;

  private boolean isRequired;
  private boolean hasValidator;
  private boolean hasValidation;

  public boolean isRequired() {
    return isRequired;
  }

  public boolean hasValidator() {
    return hasValidator;
  }

  public boolean hasValidation() {
    return hasValidation;
  }

  public FieldName getName() {
    return name;
  }

  public List<Modifier> getModifiers() {
    return modifiers;
  }

  public Optional<FieldComment> getComment() {
    return comment;
  }

  public FieldType getType() {
    return type;
  }

  public List<FieldValidation> getValidations() {
    return validations;
  }

  public List<FieldValidator> getValidators() {
    return validators;
  }

  @Override
  public String toString() {
    return (
      "EntityField{" +
      "name=" +
      name +
      ", modifiers=" +
      modifiers +
      ", comment=" +
      comment +
      ", type=" +
      type +
      ", validations=" +
      validations +
      ", validators=" +
      validators +
      ", isRequired=" +
      isRequired +
      ", hasValidator=" +
      hasValidator +
      ", hasValidation=" +
      hasValidation +
      '}'
    );
  }

  public static final class FieldBuilder {

    private FieldName name;
    private FieldType type;
    private final List<Modifier> modifiers = new ArrayList<>();
    private FieldComment comment;
    private final List<FieldValidation> validations = new ArrayList<>();
    private final List<FieldValidator> validators = new ArrayList<>();

    private FieldBuilder() {}

    public FieldBuilder name(FieldName name) {
      Assert.notNull("name", name);
      this.name = name;
      return this;
    }

    public FieldBuilder addModifiers(Modifier modifiers) {
      Assert.notNull("modifiers", modifiers);
      this.modifiers.add(modifiers);
      return this;
    }

    public FieldBuilder comment(FieldComment comment) {
      Assert.notNull("comment", comment);
      this.comment = comment;
      return this;
    }

    public FieldBuilder type(FieldType type) {
      Assert.notNull("type", type);
      this.type = type;
      return this;
    }

    public FieldBuilder addValidation(FieldValidation validation) {
      Assert.notNull("validation", validation);
      this.validations.add(validation);
      return this;
    }

    public FieldBuilder addValidator(FieldValidator validator) {
      Assert.notNull("validator", validator);
      this.validators.add(validator);
      return this;
    }

    public ClassField build() {
      Assert.notNull("name", name);
      Assert.notNull("type", type);
      ClassField classField = new ClassField();

      classField.name = this.name;
      classField.validators = this.validators;
      classField.hasValidator = !this.validators.isEmpty();
      classField.hasValidation = !this.validations.isEmpty();
      classField.isRequired = this.validations.stream().anyMatch(f -> f.equals(FieldValidation.REQUIRED));
      if (this.comment == null) {
        classField.comment = Optional.empty();
      } else {
        classField.comment = Optional.of(this.comment);
      }

      classField.type = this.type;
      if (this.modifiers.isEmpty()) {
        this.modifiers.add(Modifier.PRIVATE);
      }
      classField.modifiers = this.modifiers;
      classField.validations = this.validations;
      return classField;
    }
  }
}
