package tech.jhipster.lite.dsl.generator.clazz.domain;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import tech.jhipster.lite.dsl.parser.domain.DslAnnotation;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslContextName;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;
import tech.jhipster.lite.error.domain.Assert;

public class AnnotationConverter {

  private AnnotationConverter() {}

  public static Annotation convertAnnotation(DslAnnotation dslAnnotation) {
    if (dslAnnotation.value().isPresent()) {
      return switch (dslAnnotation.name()) {
        case "min" -> new FieldValidatorMinSize(Integer.getInteger(dslAnnotation.value().get()));
        case "max" -> new FieldValidatorMaxSize(Integer.getInteger(dslAnnotation.value().get()));
        default -> throw new AnnotationConvertionException(String.format("Annotation with value %s not recognized", dslAnnotation.name()));
      };
    } else {
      return switch (dslAnnotation.name()) {
        case "notNull" -> new FieldValidationNotNull();
        default -> new FieldAnnotationGeneric(dslAnnotation.name());
      };
    }
  }

  public static List<Annotation> convertAnnotation(List<DslAnnotation> dslAnnotation) {
    Assert.field("dslAnnotation", dslAnnotation).noNullElement();
    return dslAnnotation.stream().map(AnnotationConverter::convertAnnotation).toList();
  }
}
