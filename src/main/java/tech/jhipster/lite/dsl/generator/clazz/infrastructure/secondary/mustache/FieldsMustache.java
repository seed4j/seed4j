package tech.jhipster.lite.dsl.generator.clazz.infrastructure.secondary.mustache;

import java.util.LinkedList;
import java.util.List;
import javax.lang.model.element.Modifier;
import tech.jhipster.lite.dsl.generator.clazz.domain.FieldToGenerate;
import tech.jhipster.lite.error.domain.Assert;

public class FieldsMustache {

  public static FieldsMustacheBuilder builder() {
    return new FieldsMustacheBuilder();
  }

  private String name;
  private List<String> modifiers;
  private List<AnnotationSimple> annotationsSimple;
  private List<AnnotationValue> annotationsValue;
  private String comment;
  private String type;

  private String getterName;

  private String setterName;

  public String getName() {
    return name;
  }

  public List<String> getModifiers() {
    return modifiers;
  }

  public String getComment() {
    return comment;
  }

  public String getType() {
    return type;
  }

  public String getGetterName() {
    return getterName;
  }

  public String getSetterName() {
    return setterName;
  }

  public List<AnnotationSimple> getAnnotationsSimple() {
    return annotationsSimple;
  }

  public List<AnnotationValue> getAnnotationsValue() {
    return annotationsValue;
  }

  public static final class FieldsMustacheBuilder {

    private List<AnnotationSimple> annotationsSimple = new LinkedList<>();
    private List<AnnotationValue> annotationsValue = new LinkedList<>();
    private String comment;
    private String getterName;
    private List<String> modifiers = new LinkedList<>();
    private String name;
    private String setterName;
    private String type;

    private FieldsMustacheBuilder() {}

    public FieldsMustacheBuilder from(FieldToGenerate field) {
      Assert.notNull("field", field);
      AnnotationBuilder annotationBuilder = new AnnotationBuilder();
      field.getComment().ifPresent(fieldComment -> this.comment = fieldComment.get());
      this.name = field.getName().get();
      this.type = field.getType().name();
      this.getterName = field.getterName();
      this.setterName = field.setterName();
      this.modifiers = field.getModifiers().stream().map(Modifier::toString).toList();
      this.annotationsSimple = annotationBuilder.getAnnotationSimpleFrom(field.getAnnotations());
      this.annotationsValue = annotationBuilder.getAnnotationWithValueFrom(field.getAnnotations());
      return this;
    }

    public FieldsMustache build() {
      FieldsMustache fieldsMustache = new FieldsMustache();
      fieldsMustache.getterName = this.getterName;
      fieldsMustache.modifiers = this.modifiers;
      fieldsMustache.comment = this.comment;
      fieldsMustache.name = this.name;
      fieldsMustache.annotationsSimple = this.annotationsSimple;
      fieldsMustache.annotationsValue = this.annotationsValue;
      fieldsMustache.type = this.type;
      fieldsMustache.setterName = this.setterName;
      return fieldsMustache;
    }
  }
}
