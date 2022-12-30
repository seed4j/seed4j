package tech.jhipster.lite.dsl.parser.domain.clazz;

import java.util.*;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassComment;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassPackage;
import tech.jhipster.lite.dsl.common.domain.clazz.enums.EnumKeyValue;
import tech.jhipster.lite.dsl.common.domain.clazz.enums.EnumName;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.error.domain.Assert;

public class DslEnum {

  public static DslEnumBuilder dslEnumBuilder() {
    return new DslEnumBuilder();
  }

  private EnumName name;
  private ClassPackage packag;
  private ClassComment comment;
  private List<EnumKeyValue> enumKeyValues;

  private List<DslAnnotation> annotations;

  public EnumName getName() {
    return name;
  }

  public ClassPackage getPackage() {
    return packag;
  }

  public Optional<ClassComment> getComment() {
    return Optional.ofNullable(comment);
  }

  public List<EnumKeyValue> getEnumKeyValues() {
    return enumKeyValues;
  }

  public List<DslAnnotation> getAnnotations() {
    return Collections.unmodifiableList(annotations);
  }

  public static final class DslEnumBuilder {

    private EnumName name;

    private ClassPackage packag = ClassPackage.EMPTY;
    private ClassComment comment;
    private List<EnumKeyValue> enumKeyValues = new LinkedList<>();
    private final List<DslAnnotation> annotations = new ArrayList<>();

    private DslEnumBuilder() {}

    public DslEnumBuilder name(EnumName name) {
      Assert.notNull("key", name);
      this.name = name;
      return this;
    }

    public DslEnumBuilder comment(String comment) {
      if (comment == null || comment.isBlank()) {
        return this;
      }
      this.comment = new ClassComment(comment);
      return this;
    }

    public DslEnumBuilder addEnumKeyValue(EnumKeyValue enumKeyValue) {
      Assert.notNull("enumKeyValue", enumKeyValue);
      this.enumKeyValues.add(enumKeyValue);
      return this;
    }

    public DslEnumBuilder addAnnotation(DslAnnotation annotation) {
      Assert.notNull("annotation", annotation);
      this.annotations.add(annotation);
      return this;
    }

    public DslEnumBuilder definePackage(ClassPackage definePackage) {
      Assert.notNull("definePackage", definePackage);
      this.packag = definePackage;
      return this;
    }

    public DslEnumBuilder from(DslEnum dslEnum) {
      Assert.notNull("dslEnum", dslEnum);

      this.name = dslEnum.name;
      this.comment = dslEnum.comment;
      this.enumKeyValues.addAll(dslEnum.enumKeyValues);
      this.packag = dslEnum.packag;
      this.annotations.addAll(dslEnum.annotations);
      return this;
    }

    public DslEnum build() {
      Assert.notNull("key", this.name);
      DslEnum dslEnum = new DslEnum();

      dslEnum.name = this.name;
      dslEnum.comment = this.comment;
      dslEnum.enumKeyValues = this.enumKeyValues;
      dslEnum.packag = this.packag;
      dslEnum.annotations = this.annotations;
      return dslEnum;
    }
  }
}
