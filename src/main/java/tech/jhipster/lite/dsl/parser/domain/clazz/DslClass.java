package tech.jhipster.lite.dsl.parser.domain.clazz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassComment;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassName;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassPackage;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassType;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.ClassField;
import tech.jhipster.lite.error.domain.Assert;

public class DslClass {

  public static DslClassBuilder dslClassBuilder() {
    return new DslClassBuilder();
  }

  private ClassName name;
  private ClassType type;
  private ClassPackage packag;
  private List<ClassField> classFields;

  private List<DslAnnotation> annotations;
  private ClassComment comment;

  public ClassName getName() {
    return name;
  }

  public ClassPackage getPackage() {
    return packag;
  }

  public List<ClassField> getFields() {
    return classFields;
  }

  public Optional<ClassComment> getComment() {
    return Optional.ofNullable(comment);
  }

  public List<DslAnnotation> getAnnotations() {
    return Collections.unmodifiableList(annotations);
  }

  public ClassType getType() {
    return type;
  }

  @Override
  public String toString() {
    return (
      "DslClass{" + "key=" + name + ", type" + type + ", package=" + packag + ", fields=" + classFields + ", comment=" + comment + '}'
    );
  }

  public static final class DslClassBuilder {

    private ClassName name;

    private ClassType type = ClassType.CLASS;
    private ClassPackage packag = ClassPackage.EMPTY;
    private final List<ClassField> classFields = new ArrayList<>();
    private final List<DslAnnotation> annotations = new ArrayList<>();
    private ClassComment comment;

    private DslClassBuilder() {}

    public DslClassBuilder name(ClassName name) {
      Assert.notNull("key", name);
      this.name = name;
      return this;
    }

    public DslClassBuilder type(ClassType type) {
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
      this.annotations.add(annotation);
      return this;
    }

    public DslClassBuilder comment(ClassComment comment) {
      Assert.notNull("comment", comment);
      this.comment = comment;
      return this;
    }

    public DslClassBuilder definePackage(ClassPackage definePackage) {
      Assert.notNull("definePackage", definePackage);
      this.packag = definePackage;
      return this;
    }

    public DslClass build() {
      Assert.notNull("key", this.name);
      DslClass dslClass = new DslClass();

      dslClass.name = this.name;
      dslClass.type = this.type;
      dslClass.comment = this.comment;

      dslClass.classFields = this.classFields;
      dslClass.packag = this.packag;
      dslClass.annotations = this.annotations;
      return dslClass;
    }
  }
}
