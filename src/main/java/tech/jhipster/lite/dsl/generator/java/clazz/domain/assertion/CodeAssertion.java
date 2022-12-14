package tech.jhipster.lite.dsl.generator.java.clazz.domain.assertion;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.annotation.Annotation;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.ClassField;
import tech.jhipster.lite.error.domain.Assert;

public class CodeAssertion {

  public static AssertionBuilder builder() {
    return new AssertionBuilder();
  }

  private static final Logger log = LoggerFactory.getLogger(CodeAssertion.class);

  private boolean notNull;
  private boolean isField;
  private boolean notBlank;
  private boolean isStringMin;
  private boolean isStringMax;
  private boolean isIntegerMin;
  private boolean isIntegerMax;
  private boolean isLongMin;
  private boolean isLongMax;
  private boolean isFloatMin;
  private boolean isFloatMax;

  private boolean isDoubleMin;
  private boolean isDoubleMax;

  private boolean isBigDecimalMin;
  private boolean isBigDecimalMax;

  private boolean isBigCollectionMaxSize;
  private Integer min;
  private Integer max;
  private boolean isPositive;

  public boolean isNotNull() {
    return notNull;
  }

  public boolean isField() {
    return isField;
  }

  public boolean isNotBlank() {
    return notBlank;
  }

  public boolean isStringMin() {
    return isStringMin;
  }

  public boolean isStringMax() {
    return isStringMax;
  }

  public boolean isIntegerMin() {
    return isIntegerMin;
  }

  public boolean isIntegerMax() {
    return isIntegerMax;
  }

  public boolean isLongMin() {
    return isLongMin;
  }

  public boolean isLongMax() {
    return isLongMax;
  }

  public boolean isFloatMin() {
    return isFloatMin;
  }

  public boolean isFloatMax() {
    return isFloatMax;
  }

  public boolean isDoubleMin() {
    return isDoubleMin;
  }

  public boolean isDoubleMax() {
    return isDoubleMax;
  }

  public boolean isBigDecimalMin() {
    return isBigDecimalMin;
  }

  public boolean isBigDecimalMax() {
    return isBigDecimalMax;
  }

  public boolean isBigCollectionMaxSize() {
    return isBigCollectionMaxSize;
  }

  public Integer getMin() {
    return min;
  }

  public Integer getMax() {
    return max;
  }

  public boolean isPositive() {
    return isPositive;
  }

  public List<String> getAnnotationManaged() {
    List<String> result = new LinkedList<>();

    if (notNull) {
      result.add("notNull");
    }
    if (notBlank) {
      result.add("notBlank");
    }
    if (isStringMin || isIntegerMin || isLongMin || isFloatMin || isDoubleMin || isBigDecimalMin) {
      result.add("min");
    }
    if (isStringMax || isIntegerMax || isLongMax || isFloatMax || isDoubleMax || isBigDecimalMax) {
      result.add("max");
    }
    if (isPositive) {
      result.add("positive");
    }
    return result;
  }

  public static final class AssertionBuilder {

    private Stream<String> managedType = Stream.of("Integer", "Long", "BigDecimal", "Double", "Float", "String");

    private boolean isCollectionMaxSize;
    private boolean isBigDecimalMax;
    private boolean isBigDecimalMin;
    private boolean isDoubleMax;
    private boolean isDoubleMin;
    private boolean isField;
    private boolean isFloatMax;
    private boolean isFloatMin;
    private boolean isIntegerMax;
    private boolean isIntegerMin;
    private boolean isLongMax;
    private boolean isLongMin;
    private boolean isPositive;
    private boolean isStringMax;
    private boolean isStringMin;
    private Integer max;
    private Integer min;
    private boolean notBlank;
    private boolean notNull;

    private AssertionBuilder() {}

    public AssertionBuilder isBigCollectionMaxSize(boolean isBigCollectionMaxSize) {
      this.isCollectionMaxSize = isBigCollectionMaxSize;
      return this;
    }

    public AssertionBuilder isBigDecimalMax(boolean isBigDecimalMax) {
      this.isBigDecimalMax = isBigDecimalMax;
      return this;
    }

    public AssertionBuilder isBigDecimalMin(boolean isBigDecimalMin) {
      this.isBigDecimalMin = isBigDecimalMin;
      return this;
    }

    public AssertionBuilder isDoubleMax(boolean isDoubleMax) {
      this.isDoubleMax = isDoubleMax;
      return this;
    }

    public AssertionBuilder isDoubleMin(boolean isDoubleMin) {
      this.isDoubleMin = isDoubleMin;
      return this;
    }

    public AssertionBuilder isField(boolean isField) {
      this.isField = isField;
      return this;
    }

    public AssertionBuilder isFloatMax(boolean isFloatMax) {
      this.isFloatMax = isFloatMax;
      return this;
    }

    public AssertionBuilder isFloatMin(boolean isFloatMin) {
      this.isFloatMin = isFloatMin;
      return this;
    }

    public AssertionBuilder isIntegerMax(boolean isIntegerMax) {
      this.isIntegerMax = isIntegerMax;
      return this;
    }

    public AssertionBuilder isIntegerMin(boolean isIntegerMin) {
      this.isIntegerMin = isIntegerMin;
      return this;
    }

    public AssertionBuilder isLongMax(boolean isLongMax) {
      this.isLongMax = isLongMax;
      return this;
    }

    public AssertionBuilder isLongMin(boolean isLongMin) {
      this.isLongMin = isLongMin;
      return this;
    }

    public AssertionBuilder isPositive(boolean isPositive) {
      this.isPositive = isPositive;
      return this;
    }

    public AssertionBuilder isStringMax(boolean isStringMax) {
      this.isStringMax = isStringMax;
      return this;
    }

    public AssertionBuilder isStringMin(boolean isStringMin) {
      this.isStringMin = isStringMin;
      return this;
    }

    public AssertionBuilder max(Integer max) {
      this.max = max;
      return this;
    }

    public AssertionBuilder min(Integer min) {
      this.min = min;
      return this;
    }

    public AssertionBuilder notBlank(boolean notBlank) {
      this.notBlank = notBlank;
      return this;
    }

    public AssertionBuilder notNull(boolean notNull) {
      this.notNull = notNull;
      return this;
    }

    private boolean isMinAnnotation(Annotation annotation) {
      if ("min".equalsIgnoreCase(annotation.name())) {
        annotation.value().ifPresent(val -> min = Integer.valueOf(val));
        return true;
      }
      return false;
    }

    private boolean isMaxAnnotation(Annotation annotation) {
      if ("max".equalsIgnoreCase(annotation.name())) {
        annotation.value().ifPresent(val -> max = Integer.valueOf(val));
        return true;
      }
      return false;
    }

    private boolean isNotNullAnnotation(Annotation annotation) {
      return "notNull".equalsIgnoreCase(annotation.name());
    }

    private boolean isNotBlankAnnotation(Annotation annotation) {
      return "notBlank".equalsIgnoreCase(annotation.name());
    }

    private boolean isPositiveAnnotation(Annotation annotation) {
      return "positive".equalsIgnoreCase(annotation.name());
    }

    public AssertionBuilder from(List<Annotation> lstAnnotation, ClassField field) {
      Assert.field("lstAnnotation", lstAnnotation).noNullElement();

      this.isField = managedType.anyMatch(mt -> mt.equals(field.getType().get()));

      lstAnnotation
        .stream()
        .filter(annot -> annot.value().isPresent())
        .forEach(annotation -> {
          switch (field.getType().get()) {
            case "Integer" -> {
              isIntegerMin = isIntegerMin || isMinAnnotation(annotation);
              isIntegerMax = isIntegerMax || isMaxAnnotation(annotation);
            }
            case "String" -> {
              isStringMin = isIntegerMin || isMinAnnotation(annotation);
              isStringMax = isMaxAnnotation(annotation);
            }
            case "Long" -> {
              isLongMin = isIntegerMin || isMinAnnotation(annotation);
              isLongMax = isIntegerMax || isMaxAnnotation(annotation);
            }
            case "Double" -> {
              isDoubleMin = isIntegerMin || isMinAnnotation(annotation);
              isDoubleMax = isIntegerMax || isMaxAnnotation(annotation);
            }
            case "Float" -> {
              isFloatMin = isIntegerMin || isMinAnnotation(annotation);
              isFloatMax = isIntegerMax || isMaxAnnotation(annotation);
            }
            case "BigDecimal" -> {
              isBigDecimalMin = isBigDecimalMin || isMinAnnotation(annotation);
              isBigDecimalMax = isBigDecimalMax || isMaxAnnotation(annotation);
            }
            case "Collection" -> isCollectionMaxSize = isMaxAnnotation(annotation);
            default -> log.warn("default switch for {}", field.getType().get());
          }
        });

      lstAnnotation
        .stream()
        .filter(annot -> annot.value().isEmpty())
        .forEach(annotation -> {
          notNull = isNotNullAnnotation(annotation);
          notBlank = isNotBlankAnnotation(annotation);
          isPositive = isPositiveAnnotation(annotation);
        });

      return this;
    }

    public CodeAssertion build() {
      CodeAssertion assertion = new CodeAssertion();

      assertion.min = this.min;
      assertion.max = this.max;
      assertion.isPositive = this.isPositive;
      assertion.notNull = this.notNull;
      assertion.notBlank = this.notBlank;
      assertion.isField = this.isField;

      assertion.isStringMin = this.isStringMin;
      assertion.isStringMax = this.isStringMax;

      assertion.isIntegerMin = this.isIntegerMin;
      assertion.isIntegerMax = this.isIntegerMax;

      assertion.isFloatMin = this.isFloatMin;
      assertion.isFloatMax = this.isFloatMax;

      assertion.isLongMax = this.isLongMax;
      assertion.isLongMin = this.isLongMin;
      assertion.isDoubleMax = this.isDoubleMax;
      assertion.isDoubleMin = this.isDoubleMin;

      assertion.isBigDecimalMin = this.isBigDecimalMin;
      assertion.isBigDecimalMax = this.isBigDecimalMax;

      assertion.isBigCollectionMaxSize = this.isCollectionMaxSize;

      return assertion;
    }
  }
}
