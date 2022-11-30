package tech.jhipster.lite.dsl.domain.clazz;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import tech.jhipster.lite.dsl.domain.DslAnnotation;
import tech.jhipster.lite.dsl.domain.clazz.field.ClassField;
import tech.jhipster.lite.error.domain.Assert;

public class DslClass {

  public static DslClassBuilder dslClassBuilder() {
    return new DslClassBuilder();
  }

  private DslClassName name;
  private DslClassType type;
  private DslClassPackage packag;
  private List<ClassField> classFields;

  private List<DslAnnotation> annotations;
  private Optional<DslClassComment> comment;

  public DslClassName getName() {
    return name;
  }

  public DslClassPackage getPackage() {
    return packag;
  }

  public List<ClassField> getFields() {
    return classFields;
  }

  public Optional<DslClassComment> getComment() {
    return comment;
  }

  public List<DslAnnotation> getAnnotations() {
    return annotations;
  }

  public DslClassType getType() {
    return type;
  }

  @Override
  public String toString() {
    return (
      "DslClass{" + "name=" + name + ", type" + type + ", package=" + packag + ", fields=" + classFields + ", comment=" + comment + '}'
    );
  }

  public static final class DslClassBuilder {

    private DslClassName name;

    private DslClassType type = DslClassType.CLASS;
    private DslClassPackage packag = DslClassPackage.EMPTY;
    private final List<ClassField> classFields = new ArrayList<>();
    private final List<DslAnnotation> annotations = new ArrayList<>();
    private DslClassComment comment;

    private DslClassBuilder() {}

    public DslClassBuilder name(DslClassName name) {
      Assert.notNull("name", name);
      this.name = name;
      return this;
    }

    public DslClassBuilder type(DslClassType type) {
      Assert.notNull("type", type);
      this.type = type;
      return this;
    }

    public DslClassBuilder addField(ClassField classField) {
      Assert.notNull("field", classField);
      this.classFields.add(classField);
      return this;
    }

    public DslClassBuilder addAnnotation(DslAnnotation annotation) {
      Assert.notNull("annotation", annotation);

      if ("package".equalsIgnoreCase(annotation.name())) {
        annotation.value().ifPresent(s -> this.definePackage(new DslClassPackage(s)));
      } else {
        this.annotations.add(annotation);
      }
      return this;
    }

    public DslClassBuilder comment(DslClassComment comment) {
      Assert.notNull("comment", comment);
      this.comment = comment;
      return this;
    }

    public DslClassBuilder definePackage(DslClassPackage definePackage) {
      Assert.notNull("definePackage", definePackage);
      this.packag = definePackage;
      return this;
    }

    public DslClass build() {
      Assert.notNull("name", this.name);
      DslClass dslClass = new DslClass();

      dslClass.name = this.name;
      dslClass.type = this.type;
      if (this.comment == null) {
        dslClass.comment = Optional.empty();
      } else {
        dslClass.comment = Optional.of(this.comment);
      }

      dslClass.classFields = this.classFields;
      dslClass.packag = this.packag;
      dslClass.annotations = this.annotations;
      return dslClass;
    }
  }
}
