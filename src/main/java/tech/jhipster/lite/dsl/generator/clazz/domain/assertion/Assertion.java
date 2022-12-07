package tech.jhipster.lite.dsl.generator.clazz.domain.assertion;

import java.util.List;
import java.util.stream.Stream;
import tech.jhipster.lite.dsl.generator.clazz.domain.annotation.Annotation;
import tech.jhipster.lite.dsl.parser.domain.clazz.field.ClassField;
import tech.jhipster.lite.error.domain.Assert;

public class Assertion {

  public static AssertionBuilder builder() {
    return new AssertionBuilder();
  }

  private boolean notNull;
  private boolean isField;
  private boolean notBlank;
  private boolean isStringMin;
  private boolean isStringMax;
  private boolean isIntegerMin;
  private boolean isIntegerMax;
  private boolean isIntegerPositive;
  private boolean isLongMin;
  private boolean isLongMax;
  private boolean isLongPositive;
  private boolean isFloatMin;
  private boolean isFloatMax;
  private boolean isFloatPositive;

  private boolean isDoubleMin;
  private boolean isDoubleMax;
  private boolean isDoublePositive;

  private boolean isBigDecimalMin;
  private boolean isBigDecimalMax;
  private boolean isBigDecimalPositive;

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

  public boolean isIntegerPositive() {
    return isIntegerPositive;
  }

  public boolean isLongMin() {
    return isLongMin;
  }

  public boolean isLongMax() {
    return isLongMax;
  }

  public boolean isLongPositive() {
    return isLongPositive;
  }

  public boolean isFloatMin() {
    return isFloatMin;
  }

  public boolean isFloatMax() {
    return isFloatMax;
  }

  public boolean isFloatPositive() {
    return isFloatPositive;
  }

  public boolean isDoubleMin() {
    return isDoubleMin;
  }

  public boolean isDoubleMax() {
    return isDoubleMax;
  }

  public boolean isDoublePositive() {
    return isDoublePositive;
  }

  public boolean isBigDecimalMin() {
    return isBigDecimalMin;
  }

  public boolean isBigDecimalMax() {
    return isBigDecimalMax;
  }

  public boolean isBigDecimalPositive() {
    return isBigDecimalPositive;
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

  @Override
  public String toString() {
    return (
      "Assertion{" +
      "notNull=" +
      notNull +
      ", isField=" +
      isField +
      ", notBlank=" +
      notBlank +
      ", isStringMin=" +
      isStringMin +
      ", isStringMax=" +
      isStringMax +
      ", isIntegerMin=" +
      isIntegerMin +
      ", isIntegerMax=" +
      isIntegerMax +
      ", isIntegerPositive=" +
      isIntegerPositive +
      ", isLongMin=" +
      isLongMin +
      ", isLongMax=" +
      isLongMax +
      ", isLongPositive=" +
      isLongPositive +
      ", isFloatMin=" +
      isFloatMin +
      ", isFloatMax=" +
      isFloatMax +
      ", isFloatPositive=" +
      isFloatPositive +
      ", isDoubleMin=" +
      isDoubleMin +
      ", isDoubleMax=" +
      isDoubleMax +
      ", isDoublePositive=" +
      isDoublePositive +
      ", isBigDecimalMin=" +
      isBigDecimalMin +
      ", isBigDecimalMax=" +
      isBigDecimalMax +
      ", isBigDecimalPositive=" +
      isBigDecimalPositive +
      ", isBigCollectionMaxSize=" +
      isBigCollectionMaxSize +
      ", min=" +
      min +
      ", max=" +
      max +
      ", isPositive=" +
      isPositive +
      '}'
    );
  }

  public static final class AssertionBuilder {

    private Stream<String> managedType = Stream.of("Integer", "Long", "BigDecimal", "Double", "Float", "String");

    private boolean isBigCollectionMaxSize;
    private boolean isBigDecimalMax;
    private boolean isBigDecimalMin;
    private boolean isBigDecimalPositive;
    private boolean isDoubleMax;
    private boolean isDoubleMin;
    private boolean isDoublePositive;
    private boolean isField;
    private boolean isFloatMax;
    private boolean isFloatMin;
    private boolean isFloatPositive;
    private boolean isIntegerMax;
    private boolean isIntegerMin;
    private boolean isIntegerPositive;
    private boolean isLongMax;
    private boolean isLongMin;
    private boolean isLongPositive;
    private boolean isPositive;
    private boolean isStringMax;
    private boolean isStringMin;
    private Integer max;
    private Integer min;
    private boolean notBlank;
    private boolean notNull;

    private AssertionBuilder() {}

    public AssertionBuilder isBigCollectionMaxSize(boolean isBigCollectionMaxSize) {
      this.isBigCollectionMaxSize = isBigCollectionMaxSize;
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

    public AssertionBuilder isBigDecimalPositive(boolean isBigDecimalPositive) {
      this.isBigDecimalPositive = isBigDecimalPositive;
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

    public AssertionBuilder isDoublePositive(boolean isDoublePositive) {
      this.isDoublePositive = isDoublePositive;
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

    public AssertionBuilder isFloatPositive(boolean isFloatPositive) {
      this.isFloatPositive = isFloatPositive;
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

    public AssertionBuilder isIntegerPositive(boolean isIntegerPositive) {
      this.isIntegerPositive = isIntegerPositive;
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

    public AssertionBuilder isLongPositive(boolean isLongPositive) {
      this.isLongPositive = isLongPositive;
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

    public AssertionBuilder from(List<Annotation> lstAnnotation, ClassField field) {
      Assert.field("lstAnnotation", lstAnnotation).noNullElement();

      this.isField = managedType.anyMatch(mt -> mt.equals(field.getType().get()));

      lstAnnotation
        .stream()
        .filter(annot -> annot.value().isPresent())
        .forEach(annotation -> {
          switch (field.getType().get()) {
            case "Integer" -> {
              if ("min".equalsIgnoreCase(annotation.name())) {
                isIntegerMin = true;
                annotation.value().ifPresent(val -> min = Integer.valueOf(val));
              } else if ("max".equalsIgnoreCase(annotation.name())) {
                isIntegerMax = true;
                annotation.value().ifPresent(val -> max = Integer.valueOf(val));
              }
            }
            case "String" -> {
              if ("min".equalsIgnoreCase(annotation.name())) {
                isStringMin = true;
                annotation.value().ifPresent(val -> min = Integer.valueOf(val));
              } else if ("max".equalsIgnoreCase(annotation.name())) {
                isStringMax = true;
                annotation.value().ifPresent(val -> max = Integer.valueOf(val));
              }
            }
            case "Long" -> {
              if ("min".equalsIgnoreCase(annotation.name())) {
                isLongMin = true;
                annotation.value().ifPresent(val -> min = Integer.valueOf(val));
              } else if ("max".equalsIgnoreCase(annotation.name())) {
                isLongMax = true;
                annotation.value().ifPresent(val -> max = Integer.valueOf(val));
              }
            }
            case "Double" -> {
              if ("min".equalsIgnoreCase(annotation.name())) {
                isDoubleMin = true;
                annotation.value().ifPresent(val -> min = Integer.valueOf(val));
              } else if ("max".equalsIgnoreCase(annotation.name())) {
                isDoubleMax = true;
                annotation.value().ifPresent(val -> max = Integer.valueOf(val));
              }
            }
            case "Float" -> {
              if ("min".equalsIgnoreCase(annotation.name())) {
                isFloatMin = true;
                annotation.value().ifPresent(val -> min = Integer.valueOf(val));
              } else if ("max".equalsIgnoreCase(annotation.name())) {
                isFloatMax = true;
                annotation.value().ifPresent(val -> max = Integer.valueOf(val));
              }
            }
            case "BigDecimal" -> {
              if ("min".equalsIgnoreCase(annotation.name())) {
                isBigDecimalMin = true;
                annotation.value().ifPresent(val -> min = Integer.valueOf(val));
              } else if ("max".equalsIgnoreCase(annotation.name())) {
                isBigDecimalMax = true;
                annotation.value().ifPresent(val -> max = Integer.valueOf(val));
              }
            }
            default -> System.out.println("default switch for " + field.getType().get());
          }
        });

      lstAnnotation
        .stream()
        .filter(annot -> annot.value().isEmpty())
        .forEach(annotation -> {
          if ("notNull".equalsIgnoreCase(annotation.name())) {
            notNull = true;
          } else if ("notBlank".equalsIgnoreCase(annotation.name())) {
            notBlank = true;
          }
        });

      return this;
    }

    public Assertion build() {
      Assertion assertion = new Assertion();

      assertion.min = this.min;
      assertion.max = this.max;

      assertion.isStringMax = this.isStringMax;
      assertion.isDoublePositive = this.isDoublePositive;

      assertion.isFloatMax = this.isFloatMax;
      assertion.isField = this.isField;
      assertion.isStringMin = this.isStringMin;
      assertion.isIntegerPositive = this.isIntegerPositive;
      assertion.isBigDecimalMax = this.isBigDecimalMax;
      assertion.isBigDecimalPositive = this.isBigDecimalPositive;
      assertion.isIntegerMin = this.isIntegerMin;
      assertion.isPositive = this.isPositive;
      assertion.isFloatMin = this.isFloatMin;
      assertion.isLongMax = this.isLongMax;
      assertion.isLongPositive = this.isLongPositive;
      assertion.isLongMin = this.isLongMin;
      assertion.isDoubleMax = this.isDoubleMax;

      assertion.isDoubleMin = this.isDoubleMin;
      assertion.isBigDecimalMin = this.isBigDecimalMin;
      assertion.isIntegerMax = this.isIntegerMax;
      assertion.notNull = this.notNull;
      assertion.notBlank = this.notBlank;
      assertion.isBigCollectionMaxSize = this.isBigCollectionMaxSize;
      assertion.isFloatPositive = this.isFloatPositive;

      return assertion;
    }
  }
}
