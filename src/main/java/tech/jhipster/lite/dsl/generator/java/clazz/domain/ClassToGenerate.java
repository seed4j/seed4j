package tech.jhipster.lite.dsl.generator.java.clazz.domain;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.clazz.*;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.annotation.Annotation;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.error.domain.Assert;

public class ClassToGenerate implements FileInfoForGenerate {

  public static ClassToGenerateBuilder classToGenerateBuilder() {
    return new ClassToGenerateBuilder();
  }

  private Path folder;

  private Path file;
  private ClassName name;
  private ClassType type;
  private ClassPackage packag;

  private List<ClassImport> imports;
  private List<FieldToGenerate> fields;
  private List<Annotation> annotations;
  private ClassComment comment;

  private boolean generateFluent;

  public boolean isGenerateFluent() {
    return generateFluent;
  }

  @Override
  public Path getFolder() {
    return folder;
  }

  @Override
  public Path getPathFile() {
    return file;
  }

  public ClassName getName() {
    return name;
  }

  public ClassPackage getPackage() {
    return packag;
  }

  public List<FieldToGenerate> getFields() {
    return Collections.unmodifiableList(fields);
  }

  public List<ClassImport> getImports() {
    return Collections.unmodifiableList(imports);
  }

  public Optional<ClassComment> getComment() {
    return Optional.ofNullable(comment);
  }

  public List<Annotation> getAnnotations() {
    return annotations;
  }

  public ClassType getType() {
    return type;
  }

  @Override
  public String toString() {
    return (
      "ClassToGenerate{" +
      "folder=" +
      folder +
      ", file=" +
      file +
      ", key=" +
      name +
      ", type=" +
      type +
      ", packag=" +
      packag +
      ", imports=" +
      imports +
      ", fields=" +
      fields +
      ", annotations=" +
      annotations +
      ", comment=" +
      comment +
      '}'
    );
  }

  public static final class ClassToGenerateBuilder {

    private ClassName name;
    private Path folder;
    private Path file;
    private ClassType type = ClassType.CLASS;
    private ClassPackage packag = ClassPackage.EMPTY;
    private boolean generateFluent = false;

    private final List<ClassImport> imports = new ArrayList<>();
    private final List<FieldToGenerate> fields = new ArrayList<>();
    private final List<Annotation> annotations = new ArrayList<>();
    private ClassComment comment;

    private ClassToGenerateBuilder() {}

    public ClassToGenerateBuilder fromDslClass(DslClass dslClass) {
      Assert.notNull("dslClass", dslClass);
      this.name = dslClass.getName();
      this.type = dslClass.getType();
      this.comment = dslClass.getComment().orElse(null);
      this.packag = dslClass.getPackage();
      return this;
    }

    public ClassToGenerateBuilder name(ClassName name) {
      Assert.notNull("key", name);
      this.name = name;
      return this;
    }

    public ClassToGenerateBuilder generateFluent(boolean fluent) {
      this.generateFluent = fluent;
      return this;
    }

    public ClassToGenerateBuilder file(Path file) {
      Assert.notNull("file", file);
      this.file = file;
      return this;
    }

    public ClassToGenerateBuilder folder(Path folder) {
      Assert.notNull("folder", folder);
      this.folder = folder;
      return this;
    }

    public ClassToGenerateBuilder type(ClassType type) {
      Assert.notNull("type", type);
      this.type = type;
      return this;
    }

    public ClassToGenerateBuilder addImport(ClassImport classImport) {
      Assert.notNull("classImport", classImport);
      this.imports.add(classImport);
      return this;
    }

    public ClassToGenerateBuilder addField(FieldToGenerate fieldToGenerate) {
      Assert.notNull("fieldToGenerate", fieldToGenerate);
      this.fields.add(fieldToGenerate);
      fieldToGenerate.getType().getImport().ifPresent(this.imports::add);

      return this;
    }

    public ClassToGenerateBuilder addAnnotation(Annotation annotation) {
      Assert.notNull("annotation", annotation);
      this.annotations.add(annotation);

      return this;
    }

    public ClassToGenerateBuilder comment(ClassComment comment) {
      Assert.notNull("comment", comment);
      this.comment = comment;
      return this;
    }

    public ClassToGenerateBuilder definePackage(ClassPackage definePackage) {
      Assert.notNull("definePackage", definePackage);
      this.packag = definePackage;
      return this;
    }

    public ClassToGenerate build() {
      Assert.notNull("key", this.name);
      Assert.notNull("folder", this.folder);
      Assert.notNull("file", this.file);
      ClassToGenerate classToGenerate = new ClassToGenerate();
      classToGenerate.folder = this.folder;
      classToGenerate.name = this.name;
      classToGenerate.type = this.type;
      classToGenerate.comment = this.comment;
      classToGenerate.imports = this.imports.stream().distinct().toList();
      classToGenerate.file = this.file;
      classToGenerate.generateFluent = this.generateFluent;
      classToGenerate.fields = this.fields;
      classToGenerate.packag = this.packag;
      classToGenerate.annotations = this.annotations;
      return classToGenerate;
    }
  }
}
