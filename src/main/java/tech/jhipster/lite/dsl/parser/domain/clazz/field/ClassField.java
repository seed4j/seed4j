package tech.jhipster.lite.dsl.parser.domain.clazz.field;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.lang.model.element.Modifier;
import tech.jhipster.lite.dsl.common.domain.clazz.field.FieldComment;
import tech.jhipster.lite.dsl.common.domain.clazz.field.FieldName;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.error.domain.Assert;

public class ClassField {

  public static FieldBuilder fieldBuilder() {
    return new FieldBuilder();
  }

  private ClassField() {}

  private FieldName name;
  private List<Modifier> modifiers;
  private FieldComment comment;
  private FieldType type;

  private List<Constraint> constraints;

  private List<DslAnnotation> annotation;

  public FieldName getName() {
    return name;
  }

  public List<Modifier> getModifiers() {
    return modifiers;
  }

  public Optional<FieldComment> getComment() {
    return Optional.ofNullable(comment);
  }

  public FieldType getType() {
    return type;
  }

  public List<Constraint> getConstraints() {
    return Collections.unmodifiableList(constraints);
  }

  public List<DslAnnotation> getAnnotation() {
    return Collections.unmodifiableList(annotation);
  }

  @Override
  public String toString() {
    return (
      "ClassField{" +
      "key=" +
      name +
      ", modifiers=" +
      modifiers +
      ", comment=" +
      comment +
      ", type=" +
      type +
      ", constraints=" +
      constraints +
      ", annotation=" +
      annotation +
      '}'
    );
  }

  public static final class FieldBuilder {

    private FieldName name;
    private FieldType type;
    private final List<Modifier> modifiers = new LinkedList<>();
    private FieldComment comment;

    private final List<Constraint> constraints = new LinkedList<>();
    private final List<DslAnnotation> annotation = new LinkedList<>();

    private FieldBuilder() {}

    public FieldBuilder name(FieldName name) {
      Assert.notNull("key", name);
      this.name = name;
      return this;
    }

    public FieldBuilder addModifiers(Modifier modifiers) {
      Assert.notNull("modifiers", modifiers);
      this.modifiers.add(modifiers);
      return this;
    }

    public FieldBuilder addAnnotation(DslAnnotation annotation) {
      Assert.notNull("annotation", annotation);
      this.annotation.add(annotation);
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

    public FieldBuilder addConstraints(Constraint constraint) {
      Assert.notNull("constraint", constraint);
      this.constraints.add(constraint);
      return this;
    }

    public ClassField build() {
      Assert.notNull("key", name);
      Assert.notNull("type", type);
      ClassField classField = new ClassField();

      classField.name = this.name;
      classField.comment = this.comment;

      classField.type = this.type;
      if (this.modifiers.isEmpty()) {
        this.modifiers.add(Modifier.PRIVATE);
      }
      classField.modifiers = this.modifiers;

      classField.constraints = this.constraints;
      classField.annotation = this.annotation;
      return classField;
    }
  }
}
