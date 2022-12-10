package tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary.roaster;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.ClassToGenerate;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.FieldToGenerate;

public class RoasterFieldGenerator {

  //create fluent after

  public void addProperty(FieldToGenerate field, ClassToGenerate classToGenerate, JavaClassSource javaClass) {
    var property = javaClass.addProperty(field.getType().name(), field.getName().get());
    if (field.getComment().isPresent()) {
      property.getField().getJavaDoc().setFullText(field.getComment().get().comment());
    }
    //            if (field.hasValidator()) {
    //
    //                javaClass.addImport("javax.validation.constraints.*");
    //                field.getValidators().forEach(fieldValidator -> {
    //                    AnnotationSource<JavaClassSource> fieldAnnotation = property.getField().addAnnotation();
    //                    switch (fieldValidator.key()) {
    //                        case "minlength", "min", "minbytes" ->
    //                                fieldAnnotation.setLiteralValue("min", String.valueOf(fieldValidator.value())).setName("Size");
    //                        case "maxlength", "max", "maxbytes" ->
    //                                fieldAnnotation.setLiteralValue("max", String.valueOf(fieldValidator.value())).setName("Size");
    //
    //                        default -> throw new RuntimeException("Validator not mananged yet " + fieldValidator.key());
    //                    }
    //                });
    //            }
    //            if (field.hasValidation()) {
    //                field.getValidations().forEach(fieldValidation -> {
    //                    AnnotationSource<JavaClassSource> fieldAnnotation = property.getField().addAnnotation();
    //                    if ("required".equals(fieldValidation.key())) {
    //                        fieldAnnotation.setName("NotNull");
    //                    } else {
    //                        throw new RuntimeException("Validator not mananged yet " + fieldValidation.key());
    //                    }
    //                });
    //            }
    generateFluentForProperty(field, classToGenerate.getName().get(), javaClass);
  }

  public void generateFluentForProperty(FieldToGenerate field, String entityName, JavaClassSource javaClass) {
    MethodSource<JavaClassSource> fluentMethod = javaClass.addMethod().setName(field.getName().get()).setReturnType(entityName).setPublic();
    fluentMethod.addParameter(field.getType().name(), field.getName().get());
    String body =
      """
                        this.set{{PROPERTY}}({{PARAMETER}});
                        return this;
                """.replace(
          "{{PROPERTY}}",
          StringUtils.capitalize(field.getName().get())
        )
        .replace("{{PARAMETER}}", field.getName().get());
    fluentMethod.setBody(body);
  }
}
